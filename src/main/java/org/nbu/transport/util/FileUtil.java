package org.nbu.transport.util;

import org.nbu.transport.entity.Shipment;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private static final String SHIPMENTS_DIR = "shipments";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static {
        File dir = new File(SHIPMENTS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void exportShipments(List<Shipment> shipments, String filename) {
        String txtPath = SHIPMENTS_DIR + File.separator + filename + ".txt";
        String serPath = SHIPMENTS_DIR + File.separator + filename + ".ser";

        try (PrintWriter writer = new PrintWriter(new FileWriter(txtPath))) {
            writer.println("========================================");
            writer.println("          SHIPMENTS REPORT");
            writer.println("========================================");
            writer.println();

            for (Shipment shipment : shipments) {
                writer.println("Shipment ID: " + shipment.getId());
                writer.println("Route: " + shipment.getStartPoint() + " -> " + shipment.getEndPoint());
                writer.println("Departure: " + shipment.getDepartureDate().format(DATE_FORMATTER));
                writer.println("Arrival: " + shipment.getArrivalDate().format(DATE_FORMATTER));
                writer.println("Cargo Type: " + shipment.getCargoType());

                if (shipment.getCargoType() == Shipment.CargoType.GOODS) {
                    writer.println("Weight: " + shipment.getWeight() + " kg");
                } else {
                    writer.println("Passengers: " + shipment.getPassengerCount());
                }

                writer.println("Price: " + shipment.getPrice() + " BGN");
                writer.println("Payment Status: " + (shipment.isPaid() ? "PAID" : "UNPAID"));
                writer.println("----------------------------------------");
            }

            writer.println();
            writer.println("Total Shipments: " + shipments.size());
            writer.println("========================================");

        } catch (IOException e) {
            System.err.println("Error exporting shipments to text file: " + e.getMessage());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serPath))) {
            oos.writeObject(shipments);

        } catch (IOException e) {
            System.err.println("Error serializing shipments: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Shipment> importShipments(String filename) {
        String serPath = SHIPMENTS_DIR + File.separator + filename + ".ser";
        List<Shipment> shipments = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serPath))) {
            shipments = (List<Shipment>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error importing shipments: " + e.getMessage());
        }

        return shipments;
    }
}
