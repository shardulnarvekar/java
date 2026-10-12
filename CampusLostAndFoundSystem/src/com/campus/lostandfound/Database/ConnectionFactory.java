package com.campus.lostandfound.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class ConnectionFactory {

    static final String driver = "com.mysql.cj.jdbc.Driver";
    static final String url = config("LOSTFOUND_DB_URL",
            "jdbc:mysql://localhost:3306/LostFoundDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai&characterEncoding=utf8");
    static final String username = config("LOSTFOUND_DB_USER", "root");
    static final String password = config("LOSTFOUND_DB_PASSWORD", "root");

    Connection conn = null;
    Statement statement = null;
    ResultSet resultSet = null;

    private static String config(String key, String fallback) {
        String property = System.getProperty(key);
        if (property != null && !property.trim().isEmpty()) return property.trim();
        String env = System.getenv(key);
        if (env != null && !env.trim().isEmpty()) return env.trim();
        return fallback;
    }

    public ConnectionFactory() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            statement = conn.createStatement();
            initializeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, username, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void initializeDatabase() throws SQLException {
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users ("
                        + "id int NOT NULL AUTO_INCREMENT,"
                        + "name varchar(45) NOT NULL,"
                        + "location varchar(45) NOT NULL,"
                        + "phone varchar(20) NOT NULL,"
                        + "username varchar(20) NOT NULL,"
                        + "password varchar(200) NOT NULL,"
                        + "usertype varchar(45) NOT NULL,"
                        + "PRIMARY KEY (id)"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"
        );
        statement.executeUpdate("ALTER TABLE users MODIFY phone varchar(20) NOT NULL");
        addUniqueUsernameIndexIfMissing();

        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS userlogs ("
                        + "username varchar(45) NOT NULL,"
                        + "in_time varchar(45) NOT NULL,"
                        + "out_time varchar(45) NOT NULL"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"
        );

        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS app_settings ("
                        + "setting_key varchar(50) NOT NULL PRIMARY KEY,"
                        + "setting_value varchar(100) NOT NULL"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"
        );

        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS lost_items ("
                        + "id int NOT NULL AUTO_INCREMENT,"
                        + "item_name varchar(100) NOT NULL,"
                        + "category varchar(50) NOT NULL,"
                        + "pickup_location varchar(100) NOT NULL,"
                        + "pickup_time datetime NOT NULL,"
                        + "pickup_person varchar(50) NOT NULL,"
                        + "contact varchar(50) NOT NULL,"
                        + "description text,"
                        + "status varchar(20) NOT NULL DEFAULT 'Unclaimed',"
                        + "claimant varchar(50),"
                        + "claim_contact varchar(50),"
                        + "claim_time datetime,"
                        + "submitter_username varchar(20),"
                        + "claimant_username varchar(20),"
                        + "PRIMARY KEY (id)"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"
        );
        statement.executeUpdate("ALTER TABLE lost_items MODIFY category varchar(50) NOT NULL");
        statement.executeUpdate("ALTER TABLE lost_items MODIFY status varchar(20) NOT NULL DEFAULT 'Unclaimed'");
        addColumnIfMissing("lost_items", "submitter_username", "varchar(20)");
        addColumnIfMissing("lost_items", "claimant_username", "varchar(20)");


        ensureUser("Administrator", "Campus", "13300000000", "admin", "admin", "ADMIN");
        ensureUser("Standard User", "Campus", "18256789012", "user", "user", "USER");
        migrateLegacyValues();
        seedLostItems();
    }

    private void addUniqueUsernameIndexIfMissing() {
        try {
            statement.executeUpdate("ALTER TABLE users ADD UNIQUE KEY uk_username (username)");
        } catch (SQLException ignored) {
        }
    }

    private void addColumnIfMissing(String table, String column, String definition) {
        try {
            statement.executeUpdate("ALTER TABLE " + table + " ADD COLUMN " + column + " " + definition);
        } catch (SQLException ignored) {
        }
    }

    private void ensureUser(String name, String location, String phone, String user, String pass, String userType) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT id FROM users WHERE username=? LIMIT 1")) {
            ps.setString(1, user);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return;
            }
        }

        String insertSql = "INSERT INTO users (name,location,phone,username,password,usertype) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setString(1, name);
            ps.setString(2, location);
            ps.setString(3, phone);
            ps.setString(4, user);
            ps.setString(5, pass);
            ps.setString(6, userType);
            ps.executeUpdate();
        }
    }

    private void migrateLegacyValues() throws SQLException {
        updateLegacyValue("status", "待认领", "Unclaimed");
        updateLegacyValue("status", "已认领", "Claimed");
        updateLegacyValue("status", "已移交", "Handed Over");
        updateLegacyValue("category", "电子产品", "Electronics");
        updateLegacyValue("category", "证件类", "Identification");
        updateLegacyValue("category", "衣物/服饰", "Clothing & Accessories");
        updateLegacyValue("category", "学习用品", "Study Supplies");
        updateLegacyValue("category", "生活用品", "Everyday Items");
        updateLegacyValue("category", "贵重物品", "Valuables");
        updateLegacyValue("category", "其他", "Other");
        migrateDefaultUser("admin", "管理员", "Administrator");
        migrateDefaultUser("user", "普通用户", "Standard User");
        migrateSampleItem("学生证", "图书馆二楼", "2026-05-27 09:15:00", "信息工程学院2023级，照片为短发", "李娜", null, "Student ID Card", "Library, Second Floor", "2023 Information Engineering student ID; short-haired photo.", "Li Na", null);
        migrateSampleItem("Nike 运动鞋", "五餐门口", "2026-05-29 12:30:00", "黑色42码左脚，鞋带已松", "刘明", "陈静", "Nike Sneaker", "Outside Dining Hall 5", "Black left shoe, size 42; loose laces.", "Liu Ming", "Chen Jing");
        migrateSampleItem("高数教材", "四教 205", "2026-05-26 10:45:00", "第7版，封面有明显折痕和笔记", "赵丽", null, "Calculus Textbook", "Teaching Building 4, Room 205", "Seventh edition with visible cover creases and notes.", "Zhao Li", null);
        migrateSampleItem("粉色 Stanley 保温杯", "教学区瑞幸咖啡店", "2026-05-28 15:20:00", "粉色带吸管盖，杯身有刻字", "王芳", "周菲菲", "Pink Stanley Tumbler", "Luckin Coffee, Academic Area", "Pink tumbler with a straw lid and an engraving.", "Wang Fang", "Zhou Feifei");
        migrateSampleItem("Apple Watch SE", "一教 108", "2026-05-29 08:50:00", "白色表带，屏幕右下角有细微划痕", "孙浩", null, "Apple Watch SE", "Teaching Building 1, Room 108", "White band; small scratch at the bottom right of the screen.", "Sun Hao", null);
        migrateSampleItem("小米充电宝", "图书馆自习室", "2026-05-27 18:40:00", "白色10000mAh，表面有轻微磨损", "周婷", "张伟", "Xiaomi Power Bank", "Library Study Room", "White 10,000 mAh power bank with light surface wear.", "Zhou Ting", "Zhang Wei");
        migrateSampleItem("校园卡", "二餐二楼", "2026-05-25 11:55:00", "卡面有明显磨损", "吴磊", null, "Campus Card", "Dining Hall 2, Second Floor", "Card has visible wear.", "Wu Lei", null);
        migrateSampleItem("白色连衣裙", "六教 403", "2026-05-28 16:10:00", "Zara 夏季薄款，裙摆有蕾丝", "徐静", "刘娜", "White Dress", "Teaching Building 6, Room 403", "Lightweight Zara summer dress with lace trim.", "Xu Jing", "Liu Na");
        migrateSampleItem("雷蛇机械键盘", "蜜雪冰城店", "2026-05-29 19:45:00", "黑色RGB背光，键帽有明显指纹", "陈明", null, "Razer Mechanical Keyboard", "Mixue Ice Cream & Tea", "Black RGB keyboard with fingerprints on the keys.", "Chen Ming", null);
        migrateSampleItem("联想笔记本充电器", "图书馆三楼", "2026-05-26 14:35:00", "65W 原装，线材有缠绕痕迹", "杨丽", "孙浩", "Lenovo Laptop Charger", "Library, Third Floor", "Original 65 W charger with coiled cable marks.", "Yang Li", "Sun Hao");
        migrateSampleItem("黑色折叠雨伞", "一餐门口", "2026-05-27 17:20:00", "自动开合，伞面有水渍", "胡军", null, "Black Folding Umbrella", "Outside Dining Hall 1", "Automatic open and close; water spots on the canopy.", "Hu Jun", null);
        migrateSampleItem("身份证", "教学区瑞幸咖啡店", "2026-05-29 10:15:00", "短发证件照，卡套透明", "郭佳", "周婷", "National ID Card", "Luckin Coffee, Academic Area", "Short-haired ID photo in a clear card holder.", "Guo Jia", "Zhou Ting");
        migrateSampleItem("AirPods Pro", "四教 315", "2026-05-28 13:55:00", "白色充电盒，左耳有轻微磨损", "林峰", null, "AirPods Pro", "Teaching Building 4, Room 315", "White charging case; slight wear on the left earbud.", "Lin Feng", null);
        migrateSampleItem("Jansport 双肩书包", "五教 201", "2026-05-25 09:30:00", "浅灰色，背带处有磨损", "何芳", "吴磊", "Jansport Backpack", "Teaching Building 5, Room 201", "Light gray backpack with worn straps.", "He Fang", "Wu Lei");
        migrateSampleItem("卡西欧电子手表", "六餐一楼", "2026-05-29 12:05:00", "黑色表带，屏幕显示正常", "郑凯", null, "Casio Digital Watch", "Dining Hall 6, First Floor", "Black band; display works normally.", "Zheng Kai", null);
        migrateSampleItem("黑色框近视眼镜", "二教 407", "2026-05-27 15:40:00", "细黑框，镜片度数约400度", "沈娜", "徐静", "Black Framed Glasses", "Teaching Building 2, Room 407", "Thin black frame; lenses are approximately -4.00 diopters.", "Shen Na", "Xu Jing");
        migrateSampleItem("金士顿 U盘", "图书馆一楼", "2026-05-28 20:25:00", "金属外壳，128GB", "钱明", null, "Kingston USB Drive", "Library, First Floor", "Metal casing, 128 GB.", "Qian Ming", null);
        migrateSampleItem("小熊挂件钥匙串", "三餐门口", "2026-05-26 18:50:00", "银色钥匙链，挂着棕色小熊", "韩丽", "林峰", "Bear Keychain", "Outside Dining Hall 3", "Silver keyring with a brown bear charm.", "Han Li", "Lin Feng");
        migrateSampleItem("A4 黑色笔记本", "一教 205", "2026-05-29 14:10:00", "封面有高等数学公式", "朱浩", null, "Black A4 Notebook", "Teaching Building 1, Room 205", "Cover features calculus formulas.", "Zhu Hao", null);
        migrateSampleItem("绿色帆布袋", "七教 302", "2026-05-24 10:10:00", "印有社团 logo，袋内有草稿纸", "黄晨", null, "Green Canvas Tote", "Teaching Building 7, Room 302", "Club logo on the bag; scrap paper inside.", "Huang Chen", null);
        migrateSampleItem("Kindle 阅读器", "生活区瑞幸咖啡店", "2026-05-24 19:25:00", "黑色保护套，电量较低", "唐悦", "李娜", "Kindle E-Reader", "Luckin Coffee, Residential Area", "Black protective case; low battery.", "Tang Yue", "Li Na");
        migrateSampleItem("红色围巾", "十二教 401", "2026-05-23 08:45:00", "红色针织围巾，边角有流苏", "马强", null, "Red Scarf", "Teaching Building 12, Room 401", "Red knitted scarf with fringe.", "Ma Qiang", null);
        migrateSampleItem("实验报告夹", "三教 208", "2026-05-23 16:30:00", "透明文件夹，内有物理实验报告", "宋佳", null, "Lab Report Folder", "Teaching Building 3, Room 208", "Clear folder containing a physics lab report.", "Song Jia", null);
        migrateSampleItem("华为手机", "八教 101", "2026-05-22 20:05:00", "蓝色手机壳，锁屏为风景照", "邓宇", "赵丽", "Huawei Phone", "Teaching Building 8, Room 101", "Blue case with a landscape lock screen.", "Deng Yu", "Zhao Li");
        migrateSampleItem("透明饭盒", "四餐门口", "2026-05-22 12:20:00", "透明三层饭盒，蓝色盖子", "何雨", null, "Clear Lunch Box", "Outside Dining Hall 4", "Clear three-tier lunch box with a blue lid.", "He Yu", null);
        migrateSampleItem("计算器", "九教 304", "2026-05-21 14:00:00", "卡西欧 fx-991CN，背面贴有标签", "蒋伟", "陈明", "Calculator", "Teaching Building 9, Room 304", "Casio fx-991CN with a label on the back.", "Jiang Wei", "Chen Ming");
        migrateSampleItem("白色棒球帽", "十教 206", "2026-05-20 17:45:00", "帽檐有蓝色字母", "田甜", null, "White Baseball Cap", "Teaching Building 10, Room 206", "Blue lettering on the brim.", "Tian Tian", null);
        migrateSampleItem("索尼耳机盒", "十一教 102", "2026-05-19 18:35:00", "黑色耳机盒，没有耳机", "罗成", "王芳", "Sony Earbuds Case", "Teaching Building 11, Room 102", "Black case without the earbuds.", "Luo Cheng", "Wang Fang");
        migrateSampleItem("银色项链", "二餐门口", "2026-05-18 13:15:00", "细链，吊坠为星星形状", "冯倩", null, "Silver Necklace", "Outside Dining Hall 2", "Thin chain with a star-shaped pendant.", "Feng Qian", null);
    }

    private void updateLegacyValue(String column, String oldValue, String newValue) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE lost_items SET " + column + "=? WHERE " + column + "=?")) {
            ps.setString(1, newValue);
            ps.setString(2, oldValue);
            ps.executeUpdate();
        }
    }

    private void migrateDefaultUser(String user, String oldName, String newName) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE users SET name=?, location='Campus' WHERE username=? AND name=? AND location='校园'")) {
            ps.setString(1, newName);
            ps.setString(2, user);
            ps.setString(3, oldName);
            ps.executeUpdate();
        }
    }

    private void migrateSampleItem(String oldItem, String oldLocation, String pickupTime,
                                   String oldDescription, String oldPerson, String oldClaimant,
                                   String item, String location, String description, String person,
                                   String claimant) throws SQLException {
        String sql = "UPDATE lost_items SET item_name=?, pickup_location=?, pickup_person=?, description=?, "
                + "claimant=CASE WHEN claimant=? THEN ? ELSE claimant END "
                + "WHERE item_name=? AND pickup_location=? AND pickup_time=? "
                + "AND description=? AND pickup_person=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item);
            ps.setString(2, location);
            ps.setString(3, person);
            ps.setString(4, description);
            ps.setString(5, oldClaimant);
            ps.setString(6, claimant);
            ps.setString(7, oldItem);
            ps.setString(8, oldLocation);
            ps.setTimestamp(9, Timestamp.valueOf(pickupTime));
            ps.setString(10, oldDescription);
            ps.setString(11, oldPerson);
            ps.executeUpdate();
        }
    }

    private void seedLostItems() throws SQLException {
        try (ResultSet rs = statement.executeQuery(
                "SELECT setting_value FROM app_settings WHERE setting_key='sample_items_seeded'")) {
            if (rs.next()) return;
        }
        boolean hasItems;
        try (ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM lost_items")) {
            hasItems = rs.next() && rs.getInt(1) > 0;
        }
        if (hasItems) {
            markSamplesInitialized();
            return;
        }

        insertLostItem("Student ID Card", "Identification", "Library, Second Floor", "2026-05-27 09:15:00", "Li Na", "18256789012", "2023 Information Engineering student ID; short-haired photo.", "Unclaimed", null, null, null, "user", null);
        insertLostItem("Nike Sneaker", "Clothing & Accessories", "Outside Dining Hall 5", "2026-05-29 12:30:00", "Liu Ming", "13799887766", "Black left shoe, size 42; loose laces.", "Claimed", "Chen Jing", "15012345678", "2026-05-29 13:45:00", "admin", "admin");
        insertLostItem("Calculus Textbook", "Study Supplies", "Teaching Building 4, Room 205", "2026-05-26 10:45:00", "Zhao Li", "13911223344", "Seventh edition with visible cover creases and notes.", "Unclaimed", null, null, null, "user", null);
        insertLostItem("Pink Stanley Tumbler", "Everyday Items", "Luckin Coffee, Academic Area", "2026-05-28 15:20:00", "Wang Fang", "18057280732", "Pink tumbler with a straw lid and an engraving.", "Claimed", "Zhou Feifei", "18057280735", "2026-05-28 17:05:00", "admin", "user");
        insertLostItem("Apple Watch SE", "Valuables", "Teaching Building 1, Room 108", "2026-05-29 08:50:00", "Sun Hao", "13678901234", "White band; small scratch at the bottom right of the screen.", "Unclaimed", null, null, null, "admin", null);
        insertLostItem("Xiaomi Power Bank", "Electronics", "Library Study Room", "2026-05-27 18:40:00", "Zhou Ting", "18566778899", "White 10,000 mAh power bank with light surface wear.", "Claimed", "Zhang Wei", "13812345678", "2026-05-27 20:15:00", "admin", "user");
        insertLostItem("Campus Card", "Identification", "Dining Hall 2, Second Floor", "2026-05-25 11:55:00", "Wu Lei", "17744556677", "Card has visible wear.", "Unclaimed", null, null, null, "user", null);
        insertLostItem("White Dress", "Clothing & Accessories", "Teaching Building 6, Room 403", "2026-05-28 16:10:00", "Xu Jing", "15233445566", "Lightweight Zara summer dress with lace trim.", "Claimed", "Liu Na", "13955443322", "2026-05-28 19:30:00", "admin", "admin");
        insertLostItem("Razer Mechanical Keyboard", "Electronics", "Mixue Ice Cream & Tea", "2026-05-29 19:45:00", "Chen Ming", "18311223344", "Black RGB keyboard with fingerprints on the keys.", "Unclaimed", null, null, null, "user", null);
        insertLostItem("Lenovo Laptop Charger", "Electronics", "Library, Third Floor", "2026-05-26 14:35:00", "Yang Li", "18777665544", "Original 65 W charger with coiled cable marks.", "Claimed", "Sun Hao", "13678901234", "2026-05-26 15:50:00", "admin", "admin");
        insertLostItem("Black Folding Umbrella", "Everyday Items", "Outside Dining Hall 1", "2026-05-27 17:20:00", "Hu Jun", "13155667788", "Automatic open and close; water spots on the canopy.", "Unclaimed", null, null, null, "user", null);
        insertLostItem("National ID Card", "Identification", "Luckin Coffee, Academic Area", "2026-05-29 10:15:00", "Guo Jia", "18922334455", "Short-haired ID photo in a clear card holder.", "Claimed", "Zhou Ting", "18566778899", "2026-05-29 11:40:00", "admin", "user");
        insertLostItem("AirPods Pro", "Electronics", "Teaching Building 4, Room 315", "2026-05-28 13:55:00", "Lin Feng", "17899001122", "White charging case; slight wear on the left earbud.", "Unclaimed", null, null, null, "admin", null);
        insertLostItem("Jansport Backpack", "Clothing & Accessories", "Teaching Building 5, Room 201", "2026-05-25 09:30:00", "He Fang", "15566778899", "Light gray backpack with worn straps.", "Claimed", "Wu Lei", "17744556677", "2026-05-25 14:20:00", "admin", "user");
        insertLostItem("Casio Digital Watch", "Valuables", "Dining Hall 6, First Floor", "2026-05-29 12:05:00", "Zheng Kai", "18655443322", "Black band; display works normally.", "Unclaimed", null, null, null, "user", null);
        insertLostItem("Black Framed Glasses", "Everyday Items", "Teaching Building 2, Room 407", "2026-05-27 15:40:00", "Shen Na", "13477889900", "Thin black frame; lenses are approximately -4.00 diopters.", "Claimed", "Xu Jing", "15233445566", "2026-05-27 18:10:00", "admin", "admin");
        insertLostItem("Kingston USB Drive", "Electronics", "Library, First Floor", "2026-05-28 20:25:00", "Qian Ming", "17011223344", "Metal casing, 128 GB.", "Unclaimed", null, null, null, "user", null);
        insertLostItem("Bear Keychain", "Everyday Items", "Outside Dining Hall 3", "2026-05-26 18:50:00", "Han Li", "18199887766", "Silver keyring with a brown bear charm.", "Claimed", "Lin Feng", "17899001122", "2026-05-26 21:30:00", "admin", "user");
        insertLostItem("Black A4 Notebook", "Study Supplies", "Teaching Building 1, Room 205", "2026-05-29 14:10:00", "Zhu Hao", "13344556677", "Cover features calculus formulas.", "Unclaimed", null, null, null, "user", null);

        insertLostItem("Green Canvas Tote", "Everyday Items", "Teaching Building 7, Room 302", "2026-05-24 10:10:00", "Huang Chen", "13000000001", "Club logo on the bag; scrap paper inside.", "Unclaimed", null, null, null, "user", null);
        insertLostItem("Kindle E-Reader", "Electronics", "Luckin Coffee, Residential Area", "2026-05-24 19:25:00", "Tang Yue", "13000000002", "Black protective case; low battery.", "Claimed", "Li Na", "18256789012", "2026-05-25 09:00:00", "admin", "user");
        insertLostItem("Red Scarf", "Clothing & Accessories", "Teaching Building 12, Room 401", "2026-05-23 08:45:00", "Ma Qiang", "13000000003", "Red knitted scarf with fringe.", "Unclaimed", null, null, null, "admin", null);
        insertLostItem("Lab Report Folder", "Study Supplies", "Teaching Building 3, Room 208", "2026-05-23 16:30:00", "Song Jia", "13000000004", "Clear folder containing a physics lab report.", "Handed Over", null, null, null, "admin", null);
        insertLostItem("Huawei Phone", "Valuables", "Teaching Building 8, Room 101", "2026-05-22 20:05:00", "Deng Yu", "13000000005", "Blue case with a landscape lock screen.", "Claimed", "Zhao Li", "13911223344", "2026-05-23 12:10:00", "admin", "user");
        insertLostItem("Clear Lunch Box", "Everyday Items", "Outside Dining Hall 4", "2026-05-22 12:20:00", "He Yu", "13000000006", "Clear three-tier lunch box with a blue lid.", "Unclaimed", null, null, null, "user", null);
        insertLostItem("Calculator", "Study Supplies", "Teaching Building 9, Room 304", "2026-05-21 14:00:00", "Jiang Wei", "13000000007", "Casio fx-991CN with a label on the back.", "Claimed", "Chen Ming", "18311223344", "2026-05-21 16:30:00", "admin", "admin");
        insertLostItem("White Baseball Cap", "Clothing & Accessories", "Teaching Building 10, Room 206", "2026-05-20 17:45:00", "Tian Tian", "13000000008", "Blue lettering on the brim.", "Unclaimed", null, null, null, "user", null);
        insertLostItem("Sony Earbuds Case", "Electronics", "Teaching Building 11, Room 102", "2026-05-19 18:35:00", "Luo Cheng", "13000000009", "Black case without the earbuds.", "Claimed", "Wang Fang", "18057280732", "2026-05-20 09:25:00", "admin", "user");
        insertLostItem("Silver Necklace", "Valuables", "Outside Dining Hall 2", "2026-05-18 13:15:00", "Feng Qian", "13000000010", "Thin chain with a star-shaped pendant.", "Unclaimed", null, null, null, "admin", null);
        markSamplesInitialized();
    }

    private void markSamplesInitialized() throws SQLException {
        statement.executeUpdate("INSERT IGNORE INTO app_settings (setting_key, setting_value) "
                + "VALUES ('sample_items_seeded', 'true')");
    }

    private void insertLostItem(String itemName, String category, String location, String pickupTime,
                                String pickupPerson, String contact, String description, String status,
                                String claimant, String claimContact, String claimTime, String submitter,
                                String claimantUsername) throws SQLException {
        String sql = "INSERT INTO lost_items "
                + "(item_name, category, pickup_location, pickup_time, pickup_person, contact, description, status, claimant, claim_contact, claim_time, submitter_username, claimant_username) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, itemName);
            ps.setString(2, category);
            ps.setString(3, location);
            ps.setTimestamp(4, Timestamp.valueOf(pickupTime));
            ps.setString(5, pickupPerson);
            ps.setString(6, contact);
            ps.setString(7, description);
            ps.setString(8, status);
            ps.setString(9, claimant);
            ps.setString(10, claimContact);
            ps.setTimestamp(11, claimTime == null ? null : Timestamp.valueOf(claimTime));
            ps.setString(12, submitter);
            ps.setString(13, claimantUsername);
            ps.executeUpdate();
        }
    }

    public boolean checkLogin(String username, String password, String userType) {
        String query = "SELECT * FROM users WHERE username=? AND password=? AND usertype=? LIMIT 1";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username.trim());
            ps.setString(2, password.trim());
            ps.setString(3, userType.trim());
            resultSet = ps.executeQuery();
            if (resultSet.next()) return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
