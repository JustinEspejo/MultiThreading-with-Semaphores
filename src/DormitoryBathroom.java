import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class DormitoryBathroom extends Thread {

	public static long time = System.currentTimeMillis();
	public Random rand = new Random();
    public Queue<Student> bathroom;
    public boolean bathroomIsOpen;
    

	
	DormitoryBathroom(){
		setName("Dorm");
	    bathroom = new LinkedList<Student>();

	}
	public synchronized void addBathroomQueue(Student student){ 
		bathroom.add(student); 
	}	

	
	public void run(){
		//notify that bathroom is ready
		msg("Bathroom is vacant.");
		bathroomIsOpen = true;
		int numOfStudents = BlueCollege.students.length;
		while(numOfStudents > 0){
			try {
				BlueCollege.student.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Student a = bathroom.remove();
			try {
				sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			msg(a.getName() + " is using the bathroom");
			try {
				sleep(randFiveThousand());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			a.bathroomUsed = true;
			numOfStudents--;
			BlueCollege.bathroom.release();
			try {
				sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			msg("Bathroom is empty");
		}
	}
	public void msg(String m) {
	System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
	private int randFiveThousand(){
		return rand.nextInt(5000);
	}
}
