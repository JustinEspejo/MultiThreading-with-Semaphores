import java.util.concurrent.Semaphore;

/**
 * 
 * Project 2
 * Professor: Dr. Simina Fluture
 * @author Justin Espejo
 * 		   ID: 12217389
 *
 */
public class BlueCollege {
	
	public static int numStudents, tableCapacity, numTables, studentsInTable = 0, studentsLeft;
	public static DormitoryBathroom dorm;
    public static Student students[];
    public static Teacher teacher;
    public static Semaphore bathroom = new Semaphore(0);
    public static Semaphore student = new Semaphore(0);
    public static Semaphore useBathroom = new Semaphore(1);
    public static Semaphore auditorium = new Semaphore(0);
    public static Semaphore classEnd = new Semaphore(0);
    public static Semaphore group = new Semaphore(0);
    public static Semaphore table;
    public static Semaphore dinner = new Semaphore(0);
    public static Semaphore teacherLeave = new Semaphore(0);
    public static Semaphore mutex = new Semaphore(1);
	public static void main(String[] args) {
		//Default Value
		numStudents = 15;
		tableCapacity = 4;
		numTables = 3;
		studentsLeft = numStudents;
		table = new Semaphore(numTables*tableCapacity);
		students = new Student[numStudents];
		teacher = new Teacher();
		dorm = new DormitoryBathroom();
		teacher.start();
		dorm.start();
		System.out.println("Number of students set to 10 and teacher set to 1.");
		System.out.println("A Day in the Dormitory Started:");
		for(int i = 0; i < students.length; i++){
			students[i] = new Student("Student-" + i, i);
			students[i].start();
		}
	}
	public static void dayInfoPrint(){
		System.out.println("The Daily Report");
		for(int i = 0; i < students.length; i++){
			System.out.println("Student-" + i + ": Total-" + students[i].classesAttended.size() + " : " + students[i].classesAttended);

		}
	}
	
	
}
