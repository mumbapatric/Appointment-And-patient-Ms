package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Hospital;
import project.Appointment.And.Patient.MS.repository.HospitalRepository;

import java.util.List;

@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    //add hospital
    public Hospital addHospital(Hospital hospital){
        return hospitalRepository.save(hospital);
    }

    //find all
    public List<Hospital> findAll(){
        return hospitalRepository.findAll();
    }
    // find by id
    public Hospital findById(Long id){
        return hospitalRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Hospital not found" + id));
    }

    //update hospital
    public Hospital updateHospital(Long id , Hospital updatedHospital){
        Hospital existingHospital = hospitalRepository.findById(id)
                .orElseThrow(()->new RuntimeException("hospital not found"));
        existingHospital.setName(updatedHospital.getName());
        existingHospital.setLocation(updatedHospital.getLocation());
        return hospitalRepository.save(existingHospital);
    }

    //delete hospital
    public boolean deleteHospital(Long id){
        if (hospitalRepository.existsById(id)){
            hospitalRepository.deleteById(id);
            return true;
        }
        return  false;
    }
}
