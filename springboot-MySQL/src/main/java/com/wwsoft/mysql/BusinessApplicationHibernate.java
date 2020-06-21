package com.wwsoft.mysql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.wwsoft.mysql.api.CoursesDAO;
import com.wwsoft.mysql.api.DummyService;
import com.wwsoft.mysql.api.PersonsDAO;
import com.wwsoft.mysql.api.StudentCourseDAO;
import com.wwsoft.mysql.api.StudentsDAO;
import com.wwsoft.mysql.dtos.PersonEmail;
import com.wwsoft.mysql.persistence.entities.Courses;
import com.wwsoft.mysql.persistence.entities.PersonContacts;
import com.wwsoft.mysql.persistence.entities.Persons;
import com.wwsoft.mysql.persistence.entities.StudentCourse;
import com.wwsoft.mysql.persistence.entities.StudentCoursePK;
import com.wwsoft.mysql.persistence.entities.Students;
import com.wwsoft.mysql.service.LazyService;

@Component
public class BusinessApplicationHibernate {
	private static Logger logger = Logger.getLogger("BusinessApplicationHibernate");
	
	DummyService dummyService;
	
	PersonsDAO personDAO;	
	StudentsDAO studentsDAO;
	CoursesDAO coursesDAO;
	StudentCourseDAO studentCourseDAO;
	
	@Lazy
	@Autowired
	LazyService lazyService;
	
	/**
	 * Constructor-based injection
	 * @param dbService
	 */
	@Autowired
	public BusinessApplicationHibernate(DummyService dummyService) {
		logger.info("******************  Constructor-based injection");
		this.dummyService =dummyService;
	}
	
	@PostConstruct
	public void init() {
		logger.info("******************  Post bean processing-init");
	}
	
	@PreDestroy
	public void destroy() {
		logger.info("******************  Post bean processing-destroy");
	}
	
	/**
	 * Setter-based injection
	 * @param personDAO
	 */
	@Autowired
	public void setPersonsDAO(PersonsDAO personDAO) {
		logger.info("******************  Setter-based injection for PersonsDAO");
		this.personDAO = personDAO;
	}
	
	@Autowired
	public void setStudentsDAO(StudentsDAO studentsDAO) {
		logger.info("******************  Setter-based injection for StudentsDAO");
		this.studentsDAO = studentsDAO;
	}
	
	@Autowired
	public void setCoursesDAO(CoursesDAO coursesDAO) {
		logger.info("******************  Setter-based injection for CoursesDAO");
		this.coursesDAO = coursesDAO;
	}
	
	@Autowired
	public void setStudentCourseDAO(StudentCourseDAO studentCourseDAO) {
		logger.info("******************  Setter-based injection for StudentCourseDAO");
		this.studentCourseDAO = studentCourseDAO;
	}
	
	/**
	 * This method creates Student, Courses and StudentCourse via @ManyToMany
	 * by simply saving the new Student.  However, it creates duplicate courses
	 * if those courses have been created already.
	 * @return
	 */
	private long addAll() {
		long newStudentId=0l;
		Students s = new Students();
		s.setLastName("Wang");
		s.setFirstName("Wade");
		s.setEmail("wwang@gmail.com");
		
		Courses c1 = new Courses();
	    c1.setCourseName("Java");
	    Courses c2 = new Courses();
	    c2.setCourseName("C++");
	    
	    Set<Courses> courses = new HashSet<Courses>();
        courses.add(c1);
        courses.add(c2);
        s.setCourses(courses);
        
        try {
			newStudentId = studentsDAO.addWithSave(s);
		}
		catch(Exception e) {
        	e.printStackTrace();
        }
		return newStudentId;
        
	}
	
	/**
	 * This method only creates a student without saving anything
	 * to Courses and StudentCourse tables.
	 * @return the new student ID
	 */
	private long addStudents() {
		long newStudentId=0l;
		Students s1 = new Students();
		s1.setLastName("Wang");
		s1.setFirstName("Wade");
		s1.setEmail("wwang@gmail.com");
		
		try {
			newStudentId = studentsDAO.addWithSave(s1);
		}
		catch(Exception e) {
        	e.printStackTrace();
        }
		return newStudentId;
	}
	
	/**
	 * This method creates a course.
	 * @return Array of the new course ID
	 */
	private long[] addCourses() {
		Courses c1 = new Courses();
	    c1.setCourseName("Java");
	    Courses c2 = new Courses();
	    c2.setCourseName("C++");
		
	    long[] cids = new long[2];
		try {
			cids[0] = coursesDAO.addWithSave(c1);
        	cids[1] = coursesDAO.addWithSave(c2);
		}
		catch(Exception e) {
        	e.printStackTrace();
        }
		return cids;
	}
	
	/**
	 * This method is used to create a relationship in
	 * StudentCourse after a student and a course are created.
	 * @param sid
	 * @param cids
	 */
	private void addStudentCourses(long sid, long[] cids) {
		StudentCourse sc = new StudentCourse();
		
		sc.setStudentId(sid);
		sc.setCourseId(cids[0]);	
		StudentCourse sc1 = new StudentCourse();
		
		sc1.setStudentId(sid);
		sc1.setCourseId(cids[1]);	
		
		try {
			studentCourseDAO.addWithSave(sc);
			studentCourseDAO.addWithSave(sc1);
		}
		catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	public void startManyToMany() {
		logger.info("******************  BusinessApplication startManyToMany...");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use addWithSave...");
		
		long newStudentId=0l;
		
		// newStudentId = addAll();
		
		long[] cids;
		
		newStudentId = addStudents();
		cids = addCourses();    		
		addStudentCourses(newStudentId, cids);
        
        logger.info("******************  Use addWithSave complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  getStudentWithIdUsingSessionGet: " + newStudentId);		
		try {
			Students s = studentsDAO.getStudentWithIdUsingSessionGet(newStudentId);
			Set<Courses> courseSet = s.getCourses();
			courseSet.forEach(c->System.out.println(c.getId() + "-" + c.getCourseName() + "\n"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("****************** getStudentWithIdUsingSessionGet complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  getStudentWithIdUsingSessionLoad: " + newStudentId);		
		try {
			Students s = studentsDAO.getStudentWithIdUsingSessionLoad(newStudentId);
			
			logger.info("******************  getStudentWithIdUsingSessionLoad - update scores!!! " );
			Set<Courses> courseSet = s.getCourses();
			for (Courses c: courseSet) {   
					System.out.println(c.getId() + "-" + c.getCourseName() + "\n");
					studentCourseDAO.updateScore(newStudentId, c.getId(), (byte)100);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("****************** getStudentWithIdUsingSessionLoad complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Remove id: " + 15);
		
		try {
			studentsDAO.remove(15);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("****************** Remove complete!!!");
		logger.info("****************************************************************************");
	}
	
	public void start() {
		logger.info("******************  BusinessApplication startWithHibernate...");
		logger.info("******************  the lazy bean: " + lazyService);
		
		logger.info("****************************************************************************");
		logger.info("******************  Use getWithQuery...");
		try {
			Persons person = personDAO.getWithQuery(1);			
			logger.info(person.getLastName() + "-" + person.getFirstName() );
			
			Optional<List<PersonContacts>> contacts = Optional.of(person.getPersonContacts());			
			if (contacts.isPresent()) {
			     contacts.get().forEach(c->System.out.println(c.getEmail()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use getWithQuery complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use getWithQueryHibernateTM...");
		try {
			Persons person = personDAO.getWithQueryHibernateTM(1);			
			logger.info(person.getLastName() + "-" + person.getFirstName() );
			
			Optional<List<PersonContacts>> contacts = Optional.of(person.getPersonContacts());			
			if (contacts.isPresent()) {
			     contacts.get().forEach(c->System.out.println(c.getEmail()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use getWithQueryHibernateTM complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use getWithNamedQuery...");
		try {
			Persons person = personDAO.getWithNamedQuery(1);			
			logger.info(person.getLastName() + "-" + person.getFirstName() );
			
			Optional<List<PersonContacts>> contacts = Optional.of(person.getPersonContacts());			
			if (contacts.isPresent()) {
			     contacts.get().forEach(c->System.out.println(c.getEmail()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("****************** Use getWithNamedQuery complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use getWithCriteria...");
		try {
			Persons person = personDAO.getWithCriteria(1);			
			logger.info(person.getLastName() + "-" + person.getFirstName() );
			
			Optional<List<PersonContacts>> contacts = Optional.of(person.getPersonContacts());			
			if (contacts.isPresent()) {
			     contacts.get().forEach(c->System.out.println(c.getEmail()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use getWithCriteria complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use updateEmail...");
		try {
			int i = personDAO.updateEmail(1, 'T', "dummy2@email.com");			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use updateEmail complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use addWithSave...");
		Persons savePerson = new Persons();
		long newId = 0;
		try {
			savePerson.setFirstName("New");
			savePerson.setLastName("Save");
			savePerson.setAddress("Test Dr");
			savePerson.setCity("Test city");
			PersonContacts pc = new PersonContacts();
			pc.setPerson(savePerson);
			pc.setType('U');
			pc.setEmail("save@gmail.com");
			List<PersonContacts> pcList = new ArrayList<>();
			pcList.add(pc);
			savePerson.setPersonContacts(pcList);
			newId = personDAO.addWithSave(savePerson);
			
			// try to save it again!  It will be saved as new entity!!!!!!!!!!!!
			personDAO.addWithSave(savePerson);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use addWithSave complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use addWithPersist...");
		Persons persistPerson = new Persons();
		try {
			persistPerson.setFirstName("New");
			persistPerson.setLastName("Persist");
			persistPerson.setAddress("Test Dr");
			persistPerson.setCity("Test city");
			PersonContacts pc = new PersonContacts();
			pc.setPerson(persistPerson);
			pc.setType('U');
			pc.setEmail("save@gmail.com");
			List<PersonContacts> pcList = new ArrayList<>();
			pcList.add(pc);
			persistPerson.setPersonContacts(pcList);
			personDAO.addWithPersist(persistPerson);
			
			// try to save it again!  There will be an exception as p1 is detached!!!!!!!!!!!!
			personDAO.addWithPersist(persistPerson);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use addWithPersist complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use addWtihMerge...");
		Persons mergePerson = new Persons();
		try {
			mergePerson.setFirstName("New");
			mergePerson.setLastName("merge");
			mergePerson.setAddress("Test Dr");
			mergePerson.setCity("Test city");
			PersonContacts pc = new PersonContacts();
			pc.setPerson(mergePerson);
			pc.setType('U');
			pc.setEmail("save@gmail.com");
			List<PersonContacts> pcList = new ArrayList<>();
			pcList.add(pc);
			mergePerson.setPersonContacts(pcList);
			personDAO.addWtihMerge(mergePerson);
			
			// try to save it again!  It will be saved as new entity!!!!!!!!!!!!
			mergePerson.setLastName("Merge");
			mergePerson = personDAO.addWtihMerge(mergePerson);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use addWtihMerge complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use update...");
		Persons transientPerson = new Persons();
		try {
			/**
			 * It fails to update a transient person!!!
			 */
			transientPerson.setFirstName("New");
			transientPerson.setLastName("merge");
			transientPerson.setAddress("Test Dr");
			transientPerson.setCity("Test city");
			PersonContacts pc = new PersonContacts();
			pc.setPerson(transientPerson);
			pc.setType('U');
			pc.setEmail("save@gmail.com");
			List<PersonContacts> pcList = new ArrayList<>();
			pcList.add(pc);
			transientPerson.setPersonContacts(pcList);
			personDAO.update(transientPerson);
			
			/**
			 * It's ok to update an detached entity!!!!
			 */
			persistPerson.setLastName("Update");
			personDAO.update(persistPerson);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use update complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use saveOrUpdate...");
		Persons saveOrUpdatePerson = new Persons();
		try {
			/**
			 * It's ok to saveOrUpdate a transient person!!!
			 */
			saveOrUpdatePerson.setFirstName("New");
			saveOrUpdatePerson.setLastName("saveOrUpdate");
			saveOrUpdatePerson.setAddress("Test Dr");
			saveOrUpdatePerson.setCity("Test city");
			PersonContacts pc = new PersonContacts();
			pc.setPerson(saveOrUpdatePerson);
			pc.setType('U');
			pc.setEmail("saveOrU@gmail.com");
			List<PersonContacts> pcList = new ArrayList<>();
			pcList.add(pc);
			saveOrUpdatePerson.setPersonContacts(pcList);
			personDAO.saveOrUpdate(saveOrUpdatePerson);
			
			/**
			 * It's ok to saveOrUpdate an detached entity!!!!
			 */
			persistPerson.setLastName("saveOrUpdate");
			personDAO.saveOrUpdate(persistPerson);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use saveOrUpdate complete!!!");
		logger.info("****************************************************************************");
		
		/*
		logger.info("****************************************************************************");
		logger.info("******************  Remove id: " + newId);
		try {
			personDAO.remove(newId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("****************** Remove complete!!!");
		logger.info("****************************************************************************");
		*/
		
		logger.info("****************************************************************************");
		logger.info("******************  getWithSessionGet ");
		try {
			Persons found = personDAO.getWithSessionGet(1);
			logger.info(found.getLastName() + "-" + found.getFirstName() + "-" + found.getPersonContacts().get(0).getEmail());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("****************** getWithSessionGet!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  getWithSessionLoad ");
		try {
			Persons found = personDAO.getWithSessionLoad(1);
			logger.info(found.getLastName() + "-" + found.getFirstName() + "-" + found.getPersonContacts().get(0).getEmail());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("****************** getWithSessionLoad!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  getWithNativeNamedQuery ");
		try {
			List<PersonEmail> pe = personDAO.getWithNativeNamedQuery();
			pe.forEach(pce->logger.info(pce.getLastName() + "-" + pce.getFirstName() + "-" + pce.getEmail()));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("****************** getWithNativeNamedQuery!!!");
		logger.info("****************************************************************************");
	}
}
