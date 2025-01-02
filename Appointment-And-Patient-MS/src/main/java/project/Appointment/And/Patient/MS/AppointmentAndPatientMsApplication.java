package project.Appointment.And.Patient.MS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "project.Appointment.And.Patient.MS")
public class AppointmentAndPatientMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentAndPatientMsApplication.class, args);
	}

}
