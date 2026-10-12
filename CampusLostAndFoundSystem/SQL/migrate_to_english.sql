-- Idempotent migration for installations created with Chinese status/category/sample values.
-- Run against the existing LostFoundDB database; no rows or user accounts are deleted.
USE `LostFoundDB`;
ALTER TABLE `lost_items` MODIFY `status` varchar(20) NOT NULL DEFAULT 'Unclaimed';
START TRANSACTION;

UPDATE `lost_items` SET `status` = CASE `status`
  WHEN '待认领' THEN 'Unclaimed'
  WHEN '已认领' THEN 'Claimed'
  WHEN '已移交' THEN 'Handed Over'
  ELSE `status` END
WHERE `status` IN ('待认领', '已认领', '已移交');

UPDATE `lost_items` SET `category` = CASE `category`
  WHEN '电子产品' THEN 'Electronics'
  WHEN '证件类' THEN 'Identification'
  WHEN '衣物/服饰' THEN 'Clothing & Accessories'
  WHEN '学习用品' THEN 'Study Supplies'
  WHEN '生活用品' THEN 'Everyday Items'
  WHEN '贵重物品' THEN 'Valuables'
  WHEN '其他' THEN 'Other'
  ELSE `category` END
WHERE `category` IN ('电子产品', '证件类', '衣物/服饰', '学习用品', '生活用品', '贵重物品', '其他');

UPDATE `users` SET `name`='Administrator', `location`='Campus'
WHERE `username`='admin' AND `name`='管理员' AND `location`='校园';
UPDATE `users` SET `name`='Standard User', `location`='Campus'
WHERE `username`='user' AND `name`='普通用户' AND `location`='校园';

-- Translate only untouched bundled sample fields. Edited reports remain untouched.
UPDATE `lost_items` SET `item_name`='Student ID Card', `pickup_location`='Library, Second Floor',
  `pickup_person`='Li Na', `description`='2023 Information Engineering student ID; short-haired photo.'
WHERE `item_name`='学生证' AND `pickup_location`='图书馆二楼'
  AND `pickup_time`='2026-05-27 09:15:00' AND `pickup_person`='李娜'
  AND `description`='信息工程学院2023级，照片为短发';
UPDATE `lost_items` SET `item_name`='Nike Sneaker', `pickup_location`='Outside Dining Hall 5',
  `pickup_person`='Liu Ming', `description`='Black left shoe, size 42; loose laces.',
  `claimant`=CASE WHEN `claimant`='陈静' THEN 'Chen Jing' ELSE `claimant` END
WHERE `item_name`='Nike 运动鞋' AND `pickup_location`='五餐门口'
  AND `pickup_time`='2026-05-29 12:30:00' AND `pickup_person`='刘明'
  AND `description`='黑色42码左脚，鞋带已松';
UPDATE `lost_items` SET `item_name`='Calculus Textbook', `pickup_location`='Teaching Building 4, Room 205',
  `pickup_person`='Zhao Li', `description`='Seventh edition with visible cover creases and notes.'
WHERE `item_name`='高数教材' AND `pickup_location`='四教 205'
  AND `pickup_time`='2026-05-26 10:45:00' AND `pickup_person`='赵丽'
  AND `description`='第7版，封面有明显折痕和笔记';
UPDATE `lost_items` SET `item_name`='Pink Stanley Tumbler', `pickup_location`='Luckin Coffee, Academic Area',
  `pickup_person`='Wang Fang', `description`='Pink tumbler with a straw lid and an engraving.',
  `claimant`=CASE WHEN `claimant`='周菲菲' THEN 'Zhou Feifei' ELSE `claimant` END
WHERE `item_name`='粉色 Stanley 保温杯' AND `pickup_location`='教学区瑞幸咖啡店'
  AND `pickup_time`='2026-05-28 15:20:00' AND `pickup_person`='王芳'
  AND `description`='粉色带吸管盖，杯身有刻字';
UPDATE `lost_items` SET `item_name`='Apple Watch SE', `pickup_location`='Teaching Building 1, Room 108',
  `pickup_person`='Sun Hao', `description`='White band; small scratch at the bottom right of the screen.'
WHERE `item_name`='Apple Watch SE' AND `pickup_location`='一教 108'
  AND `pickup_time`='2026-05-29 08:50:00' AND `pickup_person`='孙浩'
  AND `description`='白色表带，屏幕右下角有细微划痕';
UPDATE `lost_items` SET `item_name`='Xiaomi Power Bank', `pickup_location`='Library Study Room',
  `pickup_person`='Zhou Ting', `description`='White 10,000 mAh power bank with light surface wear.',
  `claimant`=CASE WHEN `claimant`='张伟' THEN 'Zhang Wei' ELSE `claimant` END
WHERE `item_name`='小米充电宝' AND `pickup_location`='图书馆自习室'
  AND `pickup_time`='2026-05-27 18:40:00' AND `pickup_person`='周婷'
  AND `description`='白色10000mAh，表面有轻微磨损';
UPDATE `lost_items` SET `item_name`='Campus Card', `pickup_location`='Dining Hall 2, Second Floor',
  `pickup_person`='Wu Lei', `description`='Card has visible wear.'
WHERE `item_name`='校园卡' AND `pickup_location`='二餐二楼'
  AND `pickup_time`='2026-05-25 11:55:00' AND `pickup_person`='吴磊'
  AND `description`='卡面有明显磨损';
UPDATE `lost_items` SET `item_name`='White Dress', `pickup_location`='Teaching Building 6, Room 403',
  `pickup_person`='Xu Jing', `description`='Lightweight Zara summer dress with lace trim.',
  `claimant`=CASE WHEN `claimant`='刘娜' THEN 'Liu Na' ELSE `claimant` END
WHERE `item_name`='白色连衣裙' AND `pickup_location`='六教 403'
  AND `pickup_time`='2026-05-28 16:10:00' AND `pickup_person`='徐静'
  AND `description`='Zara 夏季薄款，裙摆有蕾丝';
UPDATE `lost_items` SET `item_name`='Razer Mechanical Keyboard', `pickup_location`='Mixue Ice Cream & Tea',
  `pickup_person`='Chen Ming', `description`='Black RGB keyboard with fingerprints on the keys.'
WHERE `item_name`='雷蛇机械键盘' AND `pickup_location`='蜜雪冰城店'
  AND `pickup_time`='2026-05-29 19:45:00' AND `pickup_person`='陈明'
  AND `description`='黑色RGB背光，键帽有明显指纹';
UPDATE `lost_items` SET `item_name`='Lenovo Laptop Charger', `pickup_location`='Library, Third Floor',
  `pickup_person`='Yang Li', `description`='Original 65 W charger with coiled cable marks.',
  `claimant`=CASE WHEN `claimant`='孙浩' THEN 'Sun Hao' ELSE `claimant` END
WHERE `item_name`='联想笔记本充电器' AND `pickup_location`='图书馆三楼'
  AND `pickup_time`='2026-05-26 14:35:00' AND `pickup_person`='杨丽'
  AND `description`='65W 原装，线材有缠绕痕迹';
UPDATE `lost_items` SET `item_name`='Black Folding Umbrella', `pickup_location`='Outside Dining Hall 1',
  `pickup_person`='Hu Jun', `description`='Automatic open and close; water spots on the canopy.'
WHERE `item_name`='黑色折叠雨伞' AND `pickup_location`='一餐门口'
  AND `pickup_time`='2026-05-27 17:20:00' AND `pickup_person`='胡军'
  AND `description`='自动开合，伞面有水渍';
UPDATE `lost_items` SET `item_name`='National ID Card', `pickup_location`='Luckin Coffee, Academic Area',
  `pickup_person`='Guo Jia', `description`='Short-haired ID photo in a clear card holder.',
  `claimant`=CASE WHEN `claimant`='周婷' THEN 'Zhou Ting' ELSE `claimant` END
WHERE `item_name`='身份证' AND `pickup_location`='教学区瑞幸咖啡店'
  AND `pickup_time`='2026-05-29 10:15:00' AND `pickup_person`='郭佳'
  AND `description`='短发证件照，卡套透明';
UPDATE `lost_items` SET `item_name`='AirPods Pro', `pickup_location`='Teaching Building 4, Room 315',
  `pickup_person`='Lin Feng', `description`='White charging case; slight wear on the left earbud.'
WHERE `item_name`='AirPods Pro' AND `pickup_location`='四教 315'
  AND `pickup_time`='2026-05-28 13:55:00' AND `pickup_person`='林峰'
  AND `description`='白色充电盒，左耳有轻微磨损';
UPDATE `lost_items` SET `item_name`='Jansport Backpack', `pickup_location`='Teaching Building 5, Room 201',
  `pickup_person`='He Fang', `description`='Light gray backpack with worn straps.',
  `claimant`=CASE WHEN `claimant`='吴磊' THEN 'Wu Lei' ELSE `claimant` END
WHERE `item_name`='Jansport 双肩书包' AND `pickup_location`='五教 201'
  AND `pickup_time`='2026-05-25 09:30:00' AND `pickup_person`='何芳'
  AND `description`='浅灰色，背带处有磨损';
UPDATE `lost_items` SET `item_name`='Casio Digital Watch', `pickup_location`='Dining Hall 6, First Floor',
  `pickup_person`='Zheng Kai', `description`='Black band; display works normally.'
WHERE `item_name`='卡西欧电子手表' AND `pickup_location`='六餐一楼'
  AND `pickup_time`='2026-05-29 12:05:00' AND `pickup_person`='郑凯'
  AND `description`='黑色表带，屏幕显示正常';
UPDATE `lost_items` SET `item_name`='Black Framed Glasses', `pickup_location`='Teaching Building 2, Room 407',
  `pickup_person`='Shen Na', `description`='Thin black frame; lenses are approximately -4.00 diopters.',
  `claimant`=CASE WHEN `claimant`='徐静' THEN 'Xu Jing' ELSE `claimant` END
WHERE `item_name`='黑色框近视眼镜' AND `pickup_location`='二教 407'
  AND `pickup_time`='2026-05-27 15:40:00' AND `pickup_person`='沈娜'
  AND `description`='细黑框，镜片度数约400度';
UPDATE `lost_items` SET `item_name`='Kingston USB Drive', `pickup_location`='Library, First Floor',
  `pickup_person`='Qian Ming', `description`='Metal casing, 128 GB.'
WHERE `item_name`='金士顿 U盘' AND `pickup_location`='图书馆一楼'
  AND `pickup_time`='2026-05-28 20:25:00' AND `pickup_person`='钱明'
  AND `description`='金属外壳，128GB';
UPDATE `lost_items` SET `item_name`='Bear Keychain', `pickup_location`='Outside Dining Hall 3',
  `pickup_person`='Han Li', `description`='Silver keyring with a brown bear charm.',
  `claimant`=CASE WHEN `claimant`='林峰' THEN 'Lin Feng' ELSE `claimant` END
WHERE `item_name`='小熊挂件钥匙串' AND `pickup_location`='三餐门口'
  AND `pickup_time`='2026-05-26 18:50:00' AND `pickup_person`='韩丽'
  AND `description`='银色钥匙链，挂着棕色小熊';
UPDATE `lost_items` SET `item_name`='Black A4 Notebook', `pickup_location`='Teaching Building 1, Room 205',
  `pickup_person`='Zhu Hao', `description`='Cover features calculus formulas.'
WHERE `item_name`='A4 黑色笔记本' AND `pickup_location`='一教 205'
  AND `pickup_time`='2026-05-29 14:10:00' AND `pickup_person`='朱浩'
  AND `description`='封面有高等数学公式';
UPDATE `lost_items` SET `item_name`='Green Canvas Tote', `pickup_location`='Teaching Building 7, Room 302',
  `pickup_person`='Huang Chen', `description`='Club logo on the bag; scrap paper inside.'
WHERE `item_name`='绿色帆布袋' AND `pickup_location`='七教 302'
  AND `pickup_time`='2026-05-24 10:10:00' AND `pickup_person`='黄晨'
  AND `description`='印有社团 logo，袋内有草稿纸';
UPDATE `lost_items` SET `item_name`='Kindle E-Reader', `pickup_location`='Luckin Coffee, Residential Area',
  `pickup_person`='Tang Yue', `description`='Black protective case; low battery.',
  `claimant`=CASE WHEN `claimant`='李娜' THEN 'Li Na' ELSE `claimant` END
WHERE `item_name`='Kindle 阅读器' AND `pickup_location`='生活区瑞幸咖啡店'
  AND `pickup_time`='2026-05-24 19:25:00' AND `pickup_person`='唐悦'
  AND `description`='黑色保护套，电量较低';
UPDATE `lost_items` SET `item_name`='Red Scarf', `pickup_location`='Teaching Building 12, Room 401',
  `pickup_person`='Ma Qiang', `description`='Red knitted scarf with fringe.'
WHERE `item_name`='红色围巾' AND `pickup_location`='十二教 401'
  AND `pickup_time`='2026-05-23 08:45:00' AND `pickup_person`='马强'
  AND `description`='红色针织围巾，边角有流苏';
UPDATE `lost_items` SET `item_name`='Lab Report Folder', `pickup_location`='Teaching Building 3, Room 208',
  `pickup_person`='Song Jia', `description`='Clear folder containing a physics lab report.'
WHERE `item_name`='实验报告夹' AND `pickup_location`='三教 208'
  AND `pickup_time`='2026-05-23 16:30:00' AND `pickup_person`='宋佳'
  AND `description`='透明文件夹，内有物理实验报告';
UPDATE `lost_items` SET `item_name`='Huawei Phone', `pickup_location`='Teaching Building 8, Room 101',
  `pickup_person`='Deng Yu', `description`='Blue case with a landscape lock screen.',
  `claimant`=CASE WHEN `claimant`='赵丽' THEN 'Zhao Li' ELSE `claimant` END
WHERE `item_name`='华为手机' AND `pickup_location`='八教 101'
  AND `pickup_time`='2026-05-22 20:05:00' AND `pickup_person`='邓宇'
  AND `description`='蓝色手机壳，锁屏为风景照';
UPDATE `lost_items` SET `item_name`='Clear Lunch Box', `pickup_location`='Outside Dining Hall 4',
  `pickup_person`='He Yu', `description`='Clear three-tier lunch box with a blue lid.'
WHERE `item_name`='透明饭盒' AND `pickup_location`='四餐门口'
  AND `pickup_time`='2026-05-22 12:20:00' AND `pickup_person`='何雨'
  AND `description`='透明三层饭盒，蓝色盖子';
UPDATE `lost_items` SET `item_name`='Calculator', `pickup_location`='Teaching Building 9, Room 304',
  `pickup_person`='Jiang Wei', `description`='Casio fx-991CN with a label on the back.',
  `claimant`=CASE WHEN `claimant`='陈明' THEN 'Chen Ming' ELSE `claimant` END
WHERE `item_name`='计算器' AND `pickup_location`='九教 304'
  AND `pickup_time`='2026-05-21 14:00:00' AND `pickup_person`='蒋伟'
  AND `description`='卡西欧 fx-991CN，背面贴有标签';
UPDATE `lost_items` SET `item_name`='White Baseball Cap', `pickup_location`='Teaching Building 10, Room 206',
  `pickup_person`='Tian Tian', `description`='Blue lettering on the brim.'
WHERE `item_name`='白色棒球帽' AND `pickup_location`='十教 206'
  AND `pickup_time`='2026-05-20 17:45:00' AND `pickup_person`='田甜'
  AND `description`='帽檐有蓝色字母';
UPDATE `lost_items` SET `item_name`='Sony Earbuds Case', `pickup_location`='Teaching Building 11, Room 102',
  `pickup_person`='Luo Cheng', `description`='Black case without the earbuds.',
  `claimant`=CASE WHEN `claimant`='王芳' THEN 'Wang Fang' ELSE `claimant` END
WHERE `item_name`='索尼耳机盒' AND `pickup_location`='十一教 102'
  AND `pickup_time`='2026-05-19 18:35:00' AND `pickup_person`='罗成'
  AND `description`='黑色耳机盒，没有耳机';
UPDATE `lost_items` SET `item_name`='Silver Necklace', `pickup_location`='Outside Dining Hall 2',
  `pickup_person`='Feng Qian', `description`='Thin chain with a star-shaped pendant.'
WHERE `item_name`='银色项链' AND `pickup_location`='二餐门口'
  AND `pickup_time`='2026-05-18 13:15:00' AND `pickup_person`='冯倩'
  AND `description`='细链，吊坠为星星形状';

COMMIT;
