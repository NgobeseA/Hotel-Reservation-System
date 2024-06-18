/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.controller;

import co.za.andile.luxuryleisurehotel.dbconnect.DBConnection;
import co.za.andile.luxuryleisurehotel.room.dao.RoomDaoImpl;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.model.RoomType;
import co.za.andile.luxuryleisurehotel.room.service.RoomService;
import co.za.andile.luxuryleisurehotel.room.service.RoomServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author T440
 */
@WebServlet(name = "RoomController", urlPatterns = {"/RoomController"})
public class RoomController extends HttpServlet {
    RoomService roomService = new RoomServiceImpl(new RoomDaoImpl(new DBConnection().connect()));
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       //List<Room> rooms = roomService.getAllAvailableRooms();
       //request.setAttribute("rooms", rooms);
       //request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        switch(request.getParameter("submit")){
            case "getAddRoomPage":
                request.setAttribute("enum", RoomType.values());
                request.getRequestDispatcher("addRoom.jsp").forward(request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        switch(request.getParameter("submit")){
            case "createRoom": 
                boolean isAvailable = request.getParameter("available") != null;
                String message = (roomService.createRoom(new Room(
                        RoomType.valueOf(request.getParameter("roomType")),
                        Double.parseDouble(request.getParameter("rates")),
                        isAvailable,
                        request.getParameter("picture"),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("rating")),
                        request.getParameter("location"),
                        request.getParameter("room_number")
                ))) ? "Room added" : "failed to add room";
                    
                
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("addRoom.jsp").forward(request,response);
                
                break;
                
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp); //To change body of generated methods, choose Tools | Templates.
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
