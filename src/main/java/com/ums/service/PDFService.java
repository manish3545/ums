package com.ums.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ums.entity.Booking;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PDFService {
    public String generateBookingDetailsPdf(Booking booking) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("F://airbnb_booking//booking-confirmation" + booking.getId() + ".pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            // Title
            Chunk chunk = new Chunk("Booking Confirmation", font);
            document.add(chunk);

            // Adding a table
            PdfPTable table = new PdfPTable(2); // 2 columns


            table.addCell("Booking ID");
            table.addCell(booking.getId().toString());

            table.addCell("Guest Name");
            table.addCell(booking.getGuestName());

            table.addCell("Total Nights");
            table.addCell(booking.getTotalNights().toString());

            table.addCell("Total Price");
            table.addCell(booking.getTotalPrice().toString());

            table.addCell("Booking Date");
            table.addCell(booking.getBookingDate().toString());

            table.addCell("Check-in Time");
            table.addCell(booking.getCheckInTime().toString());



            document.add(table);
            document.close();
            return "F://airbnb_booking//booking-confirmation" + booking.getId() + ".pdf";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

