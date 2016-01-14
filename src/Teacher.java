import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class Teacher extends Thread {
	public static long time = System.currentTimeMillis();
	public Random rand = new Random();
    public Queue<Student> classLine = new LinkedList<Student>();
    public boolean classDoorOpen = true;
    public boolean schoolInSession = true;

	
	Teacher(){
		setName("Teacher");
	}
	
	public void run(){
		try {
			sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg("Class 1 will now start.");
		
		classDoorOpen = false;
		
		BlueCollege.auditorium.release(classLine.size());
		
		try {
			sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		msg("Class 1 has ended.");

		BlueCollege.classEnd.release(classLine.size());
		
		while(!classLine.isEmpty()){
			Student s = classLine.remove();
			s.classesAttended.add("Class 1");
		}
		

		
		try {
			sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		classDoorOpen = true;
		try {
			sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		msg("Class 2 will now start.");
		
		classDoorOpen = false;

		BlueCollege.auditorium.release(classLine.size());
		
		//mark all students that attended the class present
		
		try {
			sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		msg("Class 2 has ended.");
		
		BlueCollege.classEnd.release(classLine.size());
		
		while(!classLine.isEmpty()){
			Student s = classLine.remove();
			s.classesAttended.add("Class 2");
		}
		
		
		try {
			sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		classDoorOpen = true;

		try {
			sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		msg("Class 3 will now start.");
		classDoorOpen = false;

		//mark all students that attended the class present
		
//		count = BlueCollege.auditorium.availablePermits();
		BlueCollege.auditorium.release(classLine.size());
		
		try {
			sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		msg("Class 3 has ended.");
		
		BlueCollege.classEnd.release(classLine.size());

		while(!classLine.isEmpty()){
			Student s = classLine.remove();
			s.classesAttended.add("Class 3");
		}
		

		try {
			sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		schoolInSession = false;
		while (BlueCollege.studentsLeft > 0) {
			try {
				BlueCollege.dinner.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				sleep(randFiveThousand());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < BlueCollege.tableCapacity; i++)
				BlueCollege.table.release();
		}
		try {
			BlueCollege.teacherLeave.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg("Teacher is going home.");
		BlueCollege.dayInfoPrint();
	}
	
	public void msg(String m) {
	System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
	public synchronized void addClassLine(Student student){ 
		classLine.add(student); 
	}
	
	private int randFiveThousand(){
		return rand.nextInt(5000);
	}
	

}
