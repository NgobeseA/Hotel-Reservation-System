/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.controller;

import co.za.andile.luxuryleisurehotel.dbconnect.DBConnection;
import co.za.andile.luxuryleisurehotel.room.dao.RoomDaoImpl;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.roomexception.RoomExistException;
import co.za.andile.luxuryleisurehotel.room.roomtype.dao.RoomTypeDaoImpl;
import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;
//import co.za.andile.luxuryleisurehotel.room.model.RoomType;
import co.za.andile.luxuryleisurehotel.room.service.RoomService;
import co.za.andile.luxuryleisurehotel.room.service.RoomServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "RoomController", urlPatterns = {"/RoomController"})
public class RoomController extends HttpServlet {
    RoomService roomService = new RoomServiceImpl(new RoomDaoImpl(new DBConnection().connect()));
    RoomService roomTypeService = new RoomServiceImpl(new RoomTypeDaoImpl(new DBConnection().connect()));
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
               handleAddRoomPage(request, response);
                break;
            case "getRoomType":
                handleGetRoomType(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        switch(request.getParameter("submit")){
            case "createRoom": 
                // checking if check box is checked
                boolean isAvailable = request.getParameter("available") != null;
                RoomType roomtype = roomTypeService.getRoomType(Integer.parseInt(request.getParameter("roomType")));  // getting the room type
                String message;
                
                // might throw an error due to data that is being parsed on to addRoom
                try {
                    message = (roomService.createRoom(new Room(
                            roomtype,
                            Double.parseDouble(request.getParameter("rates")),
                            request.getParameter("room_number"),
                            isAvailable,
                            request.getParameter("location")
                    ))) ? "Room added" : "failed to add room";
                } catch (RoomExistException ex) {
                    message = ex.getMessage();
                }
                
                request.setAttribute("AddRoomMessage", message);
                request.getRequestDispatcher("addRoom.jsp").forward(request,response);
                
                break;
                
        }
    }

    private void handleAddRoomPage(HttpServletRequest request, HttpServletResponse response){
         List<RoomType> roomtypes = roomTypeService.getRoomTypes();
                HttpSession session = request.getSession(false);
                session.setAttribute("RoomTypes", roomtypes);
        Room room = roomService.getRoomById(Integer.parseInt(request.getParameter("roomId")));
                
        request.setAttribute("roomToedit", room);
        try {
            request.getRequestDispatcher("addRoom.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleGetRoomType()

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
