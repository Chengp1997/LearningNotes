package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import application.model.Disease;
import application.model.DiseaseChange;
import application.model.Medicine;
import application.model.MedicineChange;
import application.model.MedicineProject;
import application.model.MedicineProjectChange;
import application.model.Person;
import application.model.PersonChange;
import application.model.Services;
import application.model.ServicesChange;
import application.model.Work;
import application.model.WorkChange;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Data implements java.io.Serializable{

    public  ObservableList<Medicine> medicineData= FXCollections.observableArrayList();
    public  ObservableList<MedicineProject> medicineProjectData=FXCollections.observableArrayList();
    public  ObservableList<Services> servicesData=FXCollections.observableArrayList();
    public  ObservableList<Work> workData=FXCollections.observableArrayList();
    public  ObservableList<Person> personData=FXCollections.observableArrayList();
    public  ObservableList<Disease> diseaseData=FXCollections.observableArrayList();
    public  ArrayList<MedicineChange> mData=new ArrayList<>();
    public  ArrayList<MedicineProjectChange> mpData=new ArrayList<>();
    public  ArrayList<ServicesChange> sData=new ArrayList<>();
    public  ArrayList<WorkChange> wData=new ArrayList<>();
    public ArrayList<PersonChange> pData=new ArrayList<>();
    public ArrayList<DiseaseChange> dData=new ArrayList<>();
    //writing data to file
    public  void writeMedicine(){
        try{
      	    FileOutputStream outputStream=new FileOutputStream("medicine.txt");
      	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    	    for (Medicine medicine : medicineData) {
    	    	MedicineChange i =new MedicineChange();
            	i.initialize(medicine);
				mData.add(i);
  			}
              objectOutputStream.writeObject(mData);  
              outputStream.close();  
              mData.clear();
        }catch (FileNotFoundException e) {  
            e.printStackTrace(); 
       }catch (IOException e) {  
          e.printStackTrace();  
       } 
     }
    public void writeMedcineProject(){
    	try{
      	    FileOutputStream outputStream=new FileOutputStream("medicineProject.txt");
      	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    	    for (MedicineProject medicineProject : medicineProjectData) {
    	    	MedicineProjectChange j =new MedicineProjectChange();
            	j.initialize(medicineProject);
				mpData.add(j);
  			}
              objectOutputStream.writeObject(mpData);  
              outputStream.close();  
              mpData.clear();
        }catch (FileNotFoundException e) {  
            e.printStackTrace(); 
       }catch (IOException e) {  
          e.printStackTrace();  
       } 
    }
    
    public void writeServices(){
    	try{
      	    FileOutputStream outputStream=new FileOutputStream("services.txt");
      	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    	    for (Services services : servicesData) {
    	    	ServicesChange s =new ServicesChange();
            	s.initialize(services);
				sData.add(s);
  			}
              objectOutputStream.writeObject(sData);  
              outputStream.close();  
              sData.clear();
        }catch (FileNotFoundException e) {  
            e.printStackTrace(); 
       }catch (IOException e) {  
          e.printStackTrace();  
       } 
    }
    
    public void writeWork(){
    	try{
      	    FileOutputStream outputStream=new FileOutputStream("work.txt");
      	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    	    for (Work work : workData) {
    	    	WorkChange w =new WorkChange();
            	w.initialize(work);
				wData.add(w);
  			}
              objectOutputStream.writeObject(wData);  
              outputStream.close();  
              wData.clear();
        }catch (FileNotFoundException e) {  
            e.printStackTrace(); 
       }catch (IOException e) {  
          e.printStackTrace();  
       } 
    }
    public void writePerson(){
    	try{
      	    FileOutputStream outputStream=new FileOutputStream("person.txt");
      	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    	    for (Person person : personData) {
    	    	PersonChange w =new PersonChange();
            	w.initialize(person);
				pData.add(w);
  			}
              objectOutputStream.writeObject(pData);  
              outputStream.close();  
              pData.clear();
        }catch (FileNotFoundException e) {  
            e.printStackTrace(); 
       }catch (IOException e) {  
          e.printStackTrace();  
       } 
    }
    public  void writeDisease(){
        try{
      	    FileOutputStream outputStream=new FileOutputStream("disease.txt");
      	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    	    for (Disease disease : diseaseData) {
    	    	DiseaseChange i =new DiseaseChange();
            	i.initialize(disease);
				dData.add(i);
  			}
              objectOutputStream.writeObject(dData);  
              outputStream.close();  
              dData.clear();
        }catch (FileNotFoundException e) {  
            e.printStackTrace(); 
       }catch (IOException e) {  
          e.printStackTrace();  
       } 
     }
    
    //get data from file
      public  void readMedicine(){
      	FileInputStream reader;
      	try{
      	    reader=new FileInputStream("medicine.txt");
      	  ObjectInputStream objectInputStream=new ObjectInputStream(reader); 
      	ArrayList<MedicineChange> array= (ArrayList<MedicineChange>) objectInputStream.readObject();
      	 for (MedicineChange personProxy : array) {
 			medicineData.add(personProxy.getMedicine());
         }
      	reader.close();
         } catch (FileNotFoundException e) {  
              e.printStackTrace();  
          } catch (IOException e) {   
              e.printStackTrace();  
          } catch (ClassNotFoundException e) { 
              e.printStackTrace();  
        }   
     }
      public  void readMedicineProject(){
        	FileInputStream reader;
        	try{
        	    reader=new FileInputStream("medicineProject.txt");
        	  ObjectInputStream objectInputStream=new ObjectInputStream(reader); 
        	ArrayList<MedicineProjectChange> array= (ArrayList<MedicineProjectChange>) objectInputStream.readObject();
        	 for (MedicineProjectChange personProxy : array) {
   			medicineProjectData.add(personProxy.getMedicineProject());
           }
        	reader.close();
           } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {   
                e.printStackTrace();  
            } catch (ClassNotFoundException e) { 
                e.printStackTrace();  
          }   
       }
      
      public  void readServices(){
      	FileInputStream reader;
      	try{
      	    reader=new FileInputStream("services.txt");
      	  ObjectInputStream objectInputStream=new ObjectInputStream(reader); 
      	ArrayList<ServicesChange> array= (ArrayList<ServicesChange>) objectInputStream.readObject();
      	 for (ServicesChange personProxy : array) {
 			servicesData.add(personProxy.getServices());
         }
      	reader.close();
         } catch (FileNotFoundException e) {  
              e.printStackTrace();  
          } catch (IOException e) {   
              e.printStackTrace();  
          } catch (ClassNotFoundException e) { 
              e.printStackTrace();  
        }   
     }
      
      public  void readWork(){
        	FileInputStream reader;
        	try{
        	    reader=new FileInputStream("work.txt");
        	  ObjectInputStream objectInputStream=new ObjectInputStream(reader); 
        	ArrayList<WorkChange> array= (ArrayList<WorkChange>) objectInputStream.readObject();
        	 for (WorkChange personProxy : array) {
   			workData.add(personProxy.getWork());
           }
        	reader.close();
           } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {   
                e.printStackTrace();  
            } catch (ClassNotFoundException e) { 
                e.printStackTrace();  
          }   
       }
      
      public  void readPerson(){
      	FileInputStream reader;
      	try{
      	    reader=new FileInputStream("person.txt");
      	  ObjectInputStream objectInputStream=new ObjectInputStream(reader); 
      	ArrayList<PersonChange> array= (ArrayList<PersonChange>) objectInputStream.readObject();
      	 for (PersonChange personProxy : array) {
 			personData.add(personProxy.getPerson());
         }
      	reader.close();
         } catch (FileNotFoundException e) {  
              e.printStackTrace();  
          } catch (IOException e) {   
              e.printStackTrace();  
          } catch (ClassNotFoundException e) { 
              e.printStackTrace();  
        }   
     }
      
      public  void readDisease(){
        	FileInputStream reader;
        	try{
        	    reader=new FileInputStream("disease.txt");
        	  ObjectInputStream objectInputStream=new ObjectInputStream(reader); 
        	ArrayList<DiseaseChange> array= (ArrayList<DiseaseChange>) objectInputStream.readObject();
        	 for (DiseaseChange personProxy : array) {
   			diseaseData.add(personProxy.getDisease());
           }
        	reader.close();
           } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {   
                e.printStackTrace();  
            } catch (ClassNotFoundException e) { 
                e.printStackTrace();  
          }   
       }

      public  ObservableList<Medicine> getMedicineData(){
  		return medicineData;
  	}
      public ObservableList<MedicineProject> getMedicineProjectData(){
    	  return medicineProjectData;
      }
      
      public ObservableList<Services> getServicesData(){
    	  return servicesData;
      }
	public ObservableList<Work> getWorkData() {
		return workData;
	}
	public ObservableList<Person> getPersonData() {
		return personData;
	}
	public ObservableList<Disease> getDiseaseData() {
		return diseaseData;
	}
	



}	