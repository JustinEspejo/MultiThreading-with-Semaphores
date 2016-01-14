import java.util.LinkedList;
import java.util.Random;


public class Student extends Thread {
	public static long time = System.currentTimeMillis();
	public Random rand = new Random();
	public boolean bathroomUsed = false;
	public LinkedList<String> classesAttended = new LinkedList<String>();
	public int studentNumber;

	
	Student(String name, int studentNumber){
		this.studentNumber = studentNumber;
		setName(name);
	}
	
	public void run(){
		msg("started");
		try {
			sleep(randFiveThousand());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg("Awake and headed to the bathroom:");
		BlueCollege.dorm.addBathroomQueue(this);
		BlueCollege.student.release();

		try {
			BlueCollege.bathroom.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		msg("is done using the bathroom.");
		
		while(BlueCollege.teacher.schoolInSession){

			if(BlueCollege.teacher.classDoorOpen) {
				BlueCollege.teacher.addClassLine(this);
				msg("waiting for the teacher to arrive.");
				try {
					BlueCollege.auditorium.acquire();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				msg("is entering the auditorium to attend the class.");

				try {
					BlueCollege.classEnd.acquire();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				msg("will exit the class as it has ended.");

				try {
					sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.setPriority(MAX_PRIORITY);
				msg("sets the priority to max priority and will now have some fun.");
				try {
					sleep(randFiveThousand());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				msg("is done having fun and is now setting priority back to default.");
				this.setPriority(NORM_PRIORITY);
			} else {
				msg("could not make the class, so is walking around the campus and doing some errands.");
				try {
					sleep(randFiveThousand());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				msg("will try to join a class again.");
			}
			
		}
		
		try {
			BlueCollege.mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		BlueCollege.studentsInTable++;
		if(BlueCollege.studentsInTable % BlueCollege.tableCapacity == 0 || BlueCollege.studentsInTable == BlueCollege.numStudents) {
			BlueCollege.mutex.release();
			msg("is the last member of the group.");
			for(int i = 0; i < BlueCollege.tableCapacity - 1; i++) BlueCollege.group.release();
		} else {
			BlueCollege.mutex.release();
			msg("is waiting for a group.");
			try {
				BlueCollege.group.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		msg("is together with the group and getting a table.");
		try {
			BlueCollege.table.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		BlueCollege.dinner.release();
		msg("is done eating dinner and going back to the dorms.");
		try {
			BlueCollege.mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		BlueCollege.studentsLeft--;
		BlueCollege.mutex.release();
		if(BlueCollege.studentsLeft == 0) {
			msg("lets the Teacher know that everyone has left.");
			BlueCollege.teacherLeave.release();
		}
		
	}
	
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
	private int randFiveThousand(){
		return rand.nextInt(5000);
	}

}
