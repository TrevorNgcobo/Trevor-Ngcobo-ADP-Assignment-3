/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.ac.cput.assignment3project;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.StringTokenizer;
import java.io.Serializable;


/**
 *
 * @author Trevor Ngcobo(220477019)
 * Subject : Applications Development Practice 262S
 * Assessment : Assignment 3
 * Date: 09/06/2021
 */
public class ReadSerialFiles implements  Comparator<Stakeholder>
{
     private ObjectInputStream input;
     Stakeholder[] stakeholderArray = new Stakeholder[11];
     ArrayList<Customer> customerList = new ArrayList<Customer>();    
     ArrayList<Supplier> supplierList = new ArrayList<>();
     String [] reformedDateArray;
     String[] customerRecord = new String[6];
     String [] supplierRecord = new String[5];
     int [] customerAgeArray = new int [6];
     
     private String customerID;
     private int customerAge;
     private String dateOfBirthString;
     private int yearOfBirthInt;
     private Date dateToday = new Date();
     int currentYear;
     int canRent = 0;
     int cantRent = 0;

     
    
     public void openFile()
    {
        try 
        {            
            input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
            System.out.println("Serialized File is opened!");            
        } 
        catch (IOException ioe) 
        {
            System.out.println(ioe);
        }
    }
    
    public void readAndProcess()
    {        
        try 
        {         
            for(int i = 0; i<stakeholderArray.length; i++)
            {                
                stakeholderArray[i] = (Stakeholder)input.readObject();
            
                if(stakeholderArray[i] instanceof Customer )
                {                    
                customerList.add((Customer)stakeholderArray[i]);                
                }
                else if(stakeholderArray[i] instanceof Supplier)
                {
                supplierList.add((Supplier)stakeholderArray[i]);
                }                                            
            }
            
            Collections.sort(customerList, ReadSerialFiles.this);  
            Collections.sort(supplierList, ReadSerialFiles.this);
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }    
    }
    
    public void dateOfBirth(){
            
        try {
            for(int i =0; i<customerList.size(); i++){
            dateOfBirthString = customerList.get(i).getDateOfBirth();
            StringTokenizer dobToken = new StringTokenizer(dateOfBirthString , "-");
            String yearToken = dobToken.nextToken();
            String monthToken = dobToken.nextToken();
            String dayToken = dobToken.nextToken();
            yearOfBirthInt = Integer.valueOf(yearToken);
            currentYear = Calendar.getInstance().get(Calendar.YEAR);            
            customerAge = currentYear - yearOfBirthInt;                        
            
            int month = Integer.valueOf(monthToken);
            
            switch(month)
            {
            case 1 : monthToken = "Jan";
            break;
            case 2 : monthToken = "Feb";
            break;
            case 3 : monthToken = "Mar";
            break;
            case 4 : monthToken = "Apr";
            break;
            case 5 : monthToken = "May";
            break;
            case 6 : monthToken = "June";
            break;
            case 7 : monthToken = "July";
            break;
            case 8 : monthToken = "Aug";
            break;
            case 9 : monthToken = "Sep";
            break;
            case 10 : monthToken = "Oct";
            break;
            case 11 : monthToken = "Nov";
            break;
            case 12 : monthToken = "Dec";
            break;                        
            }
            
            String reformatedDate = dayToken+" " + monthToken+" "+ yearToken;
            reformedDateArray = new String[customerList.size()];
            reformedDateArray[i] = reformatedDate;
            customerAgeArray[i] = customerAge;  
                    
            customerRecord[i] = customerList.get(i).getStHolderId() +"\t"+ 
            customerList.get(i).getFirstName() +"\t"+ customerList.get(i).getSurName()+
            "\t"+ reformedDateArray[i]+"\t"+ customerAgeArray[i]+"\n";
            }        
            }    
        catch (Exception e)
            {
                System.out.println(e);
            }
    }
    
    
    public void supplierList()
    {
        try{        
            for(int i =0; i<supplierList.size(); i++)
            {
            supplierRecord[i] = supplierList.get(i).getStHolderId() +
                    "\t"+supplierList.get(i).getName() +"\t"+ supplierList.get(i).getProductType() + 
                    "\t\t"+ supplierList.get(i).getProductDescription() +"\n";
            }
            }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void rentCustomers()
    {
        try
        {
            for(int i =0; i<customerList.size();i++)
            {
            
            if(customerList.get(i).getCanRent() == true)
            {
            canRent++;
            }
            else
            {
            cantRent++;
            }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    
    }
    
    public void writeCustomerFile()
    {
        try
        {
            FileWriter fw = new FileWriter("customerOutFile.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println("Text file is created and open");
            
            bw.write("ID\t" + "Name\t" +"Surname\t\t" +"Date Of Birth\t"+"Age\t\n");
            bw.write("========================================================"+"\n");
            for(int i =0; i<customerList.size();i++)
            {
                dateOfBirthString = customerList.get(1).getDateOfBirth();                
                bw.write(customerRecord[i]);            
            }
            bw.write("========================================================"+"\n");
            bw.write("The number of customers who can rent :" + canRent +"\n");
            bw.write("The number of customers who cannot rent :" + cantRent);
            
            bw.close();
            System.out.println("Text file is closed");
        }        
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }    
    }
    public void writeSupplierFile()
    {
        try
        {
            FileWriter fw = new FileWriter("supplierOutFile.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println("Supplier Text file is created and open");
            
            bw.write("ID\t" + "Name\t\t" +"Prod Type\t" +"Description\t"+"\n");
            bw.write("========================================================"+"\n");
            for(int i =0; i<supplierList.size();i++)
            {
                                
                bw.write(supplierRecord[i]);            
            }
            bw.write("========================================================"+"\n");
           
            
            bw.close();
            System.out.println("Supplier file is closed");
        }        
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }    
    }
        
    public void closeSerialFile()
    {
        try 
        {
            input.close();
            System.out.println("Serialized file has been closed!");
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }        
    }
    
    public static void main(String[] args) 
    {
        CreateStakeholderSer obj1 = new CreateStakeholderSer();
        obj1.openFile();
        obj1.writeToFile();
        
        System.out.println("\nStudent's Files");
        System.out.println("==========================");
        ReadSerialFiles obj = new ReadSerialFiles();
        obj.openFile();
        obj.readAndProcess();
        obj.dateOfBirth();
        obj.rentCustomers();
        obj.writeCustomerFile();
        
        obj.supplierList();
        obj.writeSupplierFile();
        obj.closeSerialFile();    
    }

    

    @Override
    public int compare(Stakeholder o1, Stakeholder o2) 
    {
        return   o1.getStHolderId().compareTo(o2.getStHolderId());
    }
    
    



    

    
    

}
