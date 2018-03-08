/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagement_beanswork;

import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JRadioButton;


/**
 *
 * @author LambrosTzanetos
 */
public class FilterFormJFRAME extends javax.swing.JFrame {
    
    HotelDisplayJFRAME hotelDisplay;

    /**
     * Creates new form HotelDisplayJFRAME
     */
    public FilterFormJFRAME() {
        initComponents();
    }
    
    public FilterFormJFRAME(HotelDisplayJFRAME hotelDisplay) {
        initComponents();
        this.hotelDisplay = hotelDisplay;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        starsMin = new javax.swing.JSpinner();
        starsMax = new javax.swing.JSpinner();
        minCityDistance = new javax.swing.JTextField();
        maxCityDistance = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        minVisitorRating = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        doneButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        restaurant = new javax.swing.JRadioButton();
        parking = new javax.swing.JRadioButton();
        safe = new javax.swing.JRadioButton();
        pets = new javax.swing.JRadioButton();
        tv = new javax.swing.JRadioButton();
        laundry = new javax.swing.JRadioButton();
        airConditioning = new javax.swing.JRadioButton();
        internet = new javax.swing.JRadioButton();
        pool = new javax.swing.JRadioButton();
        fitness = new javax.swing.JRadioButton();
        clearButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        priceMin = new javax.swing.JTextField();
        priceMax = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        minSeminarDistance = new javax.swing.JTextField();
        maxSeminarDistance = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Hotel Filter");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Distance From City Center Range:");

        jLabel4.setText("Min:");

        jLabel5.setText("Max:");

        jLabel6.setText("Hotel Stars Range:");

        starsMin.setModel(new javax.swing.SpinnerNumberModel(0, 0, 5, 1));

        starsMax.setModel(new javax.swing.SpinnerNumberModel(5, 0, 5, 1));

        minCityDistance.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        minCityDistance.setText("0.0");

        maxCityDistance.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        maxCityDistance.setText("35.0");

        jLabel7.setText("Min:");

        jLabel8.setText("Max:");

        jLabel9.setText("Min Visitor Rating: ");

        minVisitorRating.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        minVisitorRating.setText("0");

        jLabel11.setText("Price Per Night Range");

        doneButton.setText("Done");
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        jLabel12.setText("Facilities:");

        restaurant.setText("Restaurant");
        restaurant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restaurantActionPerformed(evt);
            }
        });

        parking.setText("Parking");

        safe.setText("Safe");

        pets.setText("Pets");

        tv.setText("TV");

        laundry.setText("Laundry");

        airConditioning.setText("Air Conditioning");

        internet.setText("Internet");

        pool.setText("Pool");

        fitness.setText("Fitness");

        clearButton.setText("Clear Filter");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Min:");

        jLabel13.setText("Max:");

        priceMin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        priceMin.setText("0");

        priceMax.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        priceMax.setText("9000");
        priceMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceMaxActionPerformed(evt);
            }
        });

        jLabel14.setText("Distance From Seminar Range:");

        jLabel15.setText("Min:");

        jLabel16.setText("Max:");

        minSeminarDistance.setText("0.0");
        minSeminarDistance.setEnabled(false);
        minSeminarDistance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minSeminarDistanceActionPerformed(evt);
            }
        });

        maxSeminarDistance.setText("35.0");
        maxSeminarDistance.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(minSeminarDistance, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(jLabel16))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(safe)
                                    .addComponent(parking)
                                    .addComponent(pets)
                                    .addComponent(restaurant))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(laundry)
                                    .addComponent(internet)
                                    .addComponent(pool)
                                    .addComponent(airConditioning)
                                    .addComponent(fitness)))
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(minVisitorRating, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tv)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(priceMin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(priceMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(clearButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(maxSeminarDistance, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(minCityDistance, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(maxCityDistance, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(starsMin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel8)
                                    .addGap(18, 18, 18)
                                    .addComponent(starsMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(minCityDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxCityDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(minSeminarDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxSeminarDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(starsMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(starsMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(minVisitorRating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel13)
                    .addComponent(priceMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jLabel12)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(restaurant, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(laundry, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(parking)
                    .addComponent(airConditioning))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(safe)
                    .addComponent(internet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pets)
                    .addComponent(pool))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fitness, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tv))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(doneButton)
                    .addComponent(clearButton))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden

    }//GEN-LAST:event_formComponentHidden

    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        
        Singleton.getInstance().mainData.filteredEnabled = true;
        
        JRadioButton[] allFacilities = {restaurant, laundry, parking, airConditioning, safe, internet, pets, tv, pool, fitness};
        
        int selectedFacilitiesCounter = 0;
        for(JRadioButton check : allFacilities) {
            if(check.isSelected()) {
                selectedFacilitiesCounter += 1;
            }
        }
        String[] selectedFacilities = new String[selectedFacilitiesCounter];
        int index = 0;
        for(JRadioButton button : allFacilities) {
            if(button.isSelected()) {

                switch(button.getText()) {
                    case "Restaurant":
                        selectedFacilities[index] = "restaurant";
                        break;
                    case "Laundry":
                        selectedFacilities[index] = "laundry";
                        break;
                    case "Parking":
                        selectedFacilities[index] = "parking";
                        break;
                    case "Air Conditioning":
                        selectedFacilities[index] = "air conditioning";
                        break;
                    case "Safe":
                        selectedFacilities[index] = "safe";
                        break;
                    case "Pets":
                        selectedFacilities[index] = "pets";
                        break;
                    case "TV":
                        selectedFacilities[index] = "tv";
                        break;
                    case "Internet":
                        selectedFacilities[index] = "Wifi";
                        break;
                    case "Pool":
                        selectedFacilities[index] = "pool";
                        break;
                    case "Fitness":
                        selectedFacilities[index] = "fitness";
                        break;
                    default:
                        System.out.println("Facility Not Found");
                } 
                
                index += 1;          
            }
               
        }  
       
        if(Singleton.getInstance().mainData.seminarMode == false) {
            Singleton.getInstance().mainData.filterRooms(Double.parseDouble(minCityDistance.getText()), Double.parseDouble(maxCityDistance.getText()), (Integer) starsMin.getValue(), (Integer) starsMax.getValue(), Integer.parseInt(priceMin.getText()), Integer.parseInt(priceMax.getText()), selectedFacilities, Integer.parseInt(minVisitorRating.getText()));
            
       } else {
            Singleton.getInstance().mainData.filterRooms(Double.parseDouble(minCityDistance.getText()), Double.parseDouble(maxCityDistance.getText()), (Integer) starsMin.getValue(), (Integer) starsMax.getValue(), Integer.parseInt(priceMin.getText()), Integer.parseInt(priceMax.getText()), selectedFacilities, Double.parseDouble(minSeminarDistance.getText()), Double.parseDouble(maxSeminarDistance.getText()), Integer.parseInt(minVisitorRating.getText()));
        }
       
        hotelDisplay.setVisible(false);
        hotelDisplay.setVisible(true);
        this.setVisible(false);    
        
    }//GEN-LAST:event_doneButtonActionPerformed

    private void restaurantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restaurantActionPerformed

    }//GEN-LAST:event_restaurantActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

        if(Singleton.getInstance().mainData.seminarMode == true) {
            minSeminarDistance.setEnabled(true);
            maxSeminarDistance.setEnabled(true);
        } else {
            minSeminarDistance.setEnabled(false);
            maxSeminarDistance.setEnabled(false);
        }      
    }//GEN-LAST:event_formComponentShown

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        Singleton.getInstance().mainData.filteredEnabled = false;
        
        hotelDisplay.setVisible(false);
        hotelDisplay.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_clearButtonActionPerformed

    private void priceMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceMaxActionPerformed
     
    }//GEN-LAST:event_priceMaxActionPerformed

    private void minSeminarDistanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minSeminarDistanceActionPerformed

    }//GEN-LAST:event_minSeminarDistanceActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HotelDisplayJFRAME.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HotelDisplayJFRAME.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HotelDisplayJFRAME.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HotelDisplayJFRAME.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FilterFormJFRAME().setVisible(true);
    
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton airConditioning;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton doneButton;
    private javax.swing.JRadioButton fitness;
    private javax.swing.JRadioButton internet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton laundry;
    private javax.swing.JTextField maxCityDistance;
    private javax.swing.JTextField maxSeminarDistance;
    private javax.swing.JTextField minCityDistance;
    private javax.swing.JTextField minSeminarDistance;
    private javax.swing.JTextField minVisitorRating;
    private javax.swing.JRadioButton parking;
    private javax.swing.JRadioButton pets;
    private javax.swing.JRadioButton pool;
    private javax.swing.JTextField priceMax;
    private javax.swing.JTextField priceMin;
    private javax.swing.JRadioButton restaurant;
    private javax.swing.JRadioButton safe;
    private javax.swing.JSpinner starsMax;
    private javax.swing.JSpinner starsMin;
    private javax.swing.JRadioButton tv;
    // End of variables declaration//GEN-END:variables
}
