/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;


/**
 *
 * @author Shardul
 */
@WebServlet(urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter pw = response.getWriter();

        // Get product and quantity from HTML form
        String pname = request.getParameter("product");
        int quantity = Integer.parseInt(request.getParameter("qty"));

        // Create/Get session
        HttpSession session = request.getSession();

        // Get cart from session
        ArrayList<Cart> cart =
                (ArrayList<Cart>) session.getAttribute("cart");

        // If cart doesn't exist, create a new cart
        if (cart == null) {
            cart = new ArrayList<Cart>();
        }

        // Find product rate
        int rate = 0;

        if (pname.equals("apple")) {
            rate = 50;
        } 
        else if (pname.equals("banana")) {
            rate = 5;
        } 
        else if (pname.equals("orange")) {
            rate = 20;
        }

        // Check whether product already exists
        boolean found = false;

        for (Cart item : cart) {

            if (item.getPname().equals(pname)) {

                item.setQuantity(
                        item.getQuantity() + quantity
                );

                found = true;
                break;
            }
        }

        // If product is not already present
        if (!found) {

            Cart newItem = new Cart(pname, quantity, rate);

            cart.add(newItem);
        }

        // Store cart back in session
        session.setAttribute("cart", cart);

        pw.println("<html>");
        pw.println("<body>");

        pw.println("<h2>Product added successfully!</h2>");

        pw.println("<p>Product: " + pname + "</p>");
        pw.println("<p>Quantity: " + quantity + "</p>");
        pw.println("<p>Rate: Rs. " + rate + "</p>");

        pw.println("<br>");

        pw.println("<a href='product.html'>Add More Products</a>");
        pw.println("<br><br>");

        pw.println("<a href='ViewCartServlet'>View Cart</a>");

        pw.println("</body>");
        pw.println("</html>");
    }
}

