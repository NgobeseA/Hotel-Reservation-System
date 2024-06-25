/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.roomtype.controller;

import co.za.andile.luxuryleisurehotel.dbconnect.DBConnection;
import co.za.andile.luxuryleisurehotel.room.roomtype.dao.RoomTypeDaoImpl;
import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;
import co.za.andile.luxuryleisurehotel.room.roomtype.service.RoomTypeService;
import co.za.andile.luxuryleisurehotel.room.roomtype.service.RoomTypeServiceImpl;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author T440
 */
@WebServlet(name = "RoomTypeController", urlPatterns = {"/RoomTypeController"})
public class RoomTypeController extends HttpServlet {
    private RoomTypeService roomTypeService = new RoomTypeServiceImpl(new RoomTypeDaoImpl(new DBConnection().connect()));
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        switch(request.getParameter("submit")){
            case "getAddRoomTypePage":
                request.getRequestDispatcher("RoomController?submit=getAddRoomPage").forward(request, response);
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        switch(request.getParameter("submit")){
            case "addRoomType":
                if(roomTypeService.addRoomType(new RoomType(
                        request.getParameter("type"),
                        request.getParameter("picture_url")
                ))){
                    request.setAttribute("AddTypeMessage", "type added succesfully");
                    
                }else{
                    request.setAttribute("AddTypeMessage", "Failed to add new room type");
                }
                request.getRequestDispatcher("addRoom.jsp").forward(request, response);
                break;
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
