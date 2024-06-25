/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.home;

import co.za.andile.luxuryleisurehotel.dbconnect.DBConnection;
import co.za.andile.luxuryleisurehotel.room.dao.RoomDaoImpl;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.roomtype.dao.RoomTypeDaoImpl;
import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;
import co.za.andile.luxuryleisurehotel.room.service.RoomService;
import co.za.andile.luxuryleisurehotel.room.service.RoomServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author T440
 */
@WebServlet(name = "home", urlPatterns = {"/home"})
public class home extends HttpServlet {
    private RoomService roomService = new RoomServiceImpl(new RoomTypeDaoImpl(new DBConnection().connect()));
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        switch(request.getParameter("submit")){
            case "home":
                List<RoomType> listRooms = roomService.getRoomTypes();
                request.setAttribute("roomtypes", listRooms);
                RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
                dispatcher.forward(request, response);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
