/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.invoice;

import co.za.andile.luxuryleisurehotel.payments.model.Payment;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author T440
 */
public class Invoice {
    
    public void generateInvoice(Reservation reservation, Payment payment) throws IOException{
        String invoiceContent = generateInvoiceContent(reservation, payment);
        
        String filePath = "C:\\Users\\T440\\Desktop\\invoice_"+payment.getPayment_id()+".txt";
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(invoiceContent);
            System.out.println("File written succefully");
        }
    }
    
    private String generateInvoiceContent(Reservation reservation, Payment payment){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        StringBuilder content = new StringBuilder();
        
        content.append("Invoice ID: ").append(payment.getPayment_id()).append("\n");
        content.append("Date: ").append(payment.getPaymentDate().format(formatter)).append("\n");
        content.append("Amount: \tR").append(payment.getAmount()).append("\n");
        content.append("Account Holder: \t").append(payment.getAccHolder()).append("\n");
        
        //User Details
        content.append("\n");
        content.append("User Datails: \n");
        content.append("Name: \t").append(reservation.getUser().getName()).append("\n");
        content.append("Surname: \t").append(reservation.getUser().getSurname()).append("\n");
        content.append("Email: \t").append(reservation.getUser().getEmail());
        
        
        //Hotel Reservation
        content.append("\n");
        content.append("Hotel Details \n");
        content.append("Name: \t").append("Luxury Leisure Hotel").append("\n");
        content.append("Location: \t").append(reservation.getRoom().getLocation()).append("\n");
        content.append("Room type: \t").append(reservation.getRoom().getRoomType().getRoom_type()).append("\n");
        content.append("Room Number: \t").append(reservation.getRoom().getRoomNumber()).append("\n");
        
        
        return content.toString();
    }
}
