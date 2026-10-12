/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Shardul
 */
@WebServlet(urlPatterns = {"/ViewCartServlet"})
public class ViewCartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html");

        PrintWriter pw = response.getWriter();

        HttpSession session = request.getSession();

        ArrayList<Cart> cart =
                (ArrayList<Cart>) session.getAttribute("cart");

        pw.println("<html>");
        pw.println("<body>");

        pw.println("<h1>Shopping Cart</h1>");

        if (cart == null || cart.isEmpty()) {

            pw.println("<h3>Your cart is empty.</h3>");

        } else {

            pw.println("<table border='1'>");

            pw.println("<tr>");
            pw.println("<th>Product</th>");
            pw.println("<th>Quantity</th>");
            pw.println("<th>Rate</th>");
            pw.println("<th>Total</th>");
            pw.println("</tr>");

            int grandTotal = 0;

            for (Cart item : cart) {

                int total =
                        item.getQuantity() * item.getRate();

                grandTotal += total;

                pw.println("<tr>");

                pw.println("<td>" +
                        item.getPname() +
                        "</td>");

                pw.println("<td>" +
                        item.getQuantity() +
                        "</td>");

                pw.println("<td>" +
                        item.getRate() +
                        "</td>");

                pw.println("<td>" +
                        total +
                        "</td>");

                pw.println("</tr>");
            }

            pw.println("</table>");

            pw.println("<h3>Grand Total: Rs. "
                    + grandTotal + "</h3>");
        }

        pw.println("<br>");

        pw.println("<a href='product.html'>Add More Products</a>");

        pw.println("<br><br>");

        pw.println("<a href='ClearCartServlet'>Clear Cart</a>");

        pw.println("<br><br>");

        pw.println("<a href='LogoutServlet'>Logout</a>");

        pw.println("</body>");
        pw.println("</html>");
    }
}
