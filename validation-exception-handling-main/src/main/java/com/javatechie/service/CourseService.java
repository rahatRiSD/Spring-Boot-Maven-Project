package com.javatechie.service;

import com.javatechie.dao.CourseDao;
import com.javatechie.dto.CourseRequestDTO;
import com.javatechie.dto.CourseResponseDTO;
import com.javatechie.entity.CourseEntity;
import com.javatechie.exception.CourseServiceBusinessException;
import com.javatechie.util.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.kerberos.KerberosTicket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CourseService {

    //H2,DERBY , AeroSpike -> In memory Database

    private CourseDao courseDao;


    //create course object in DB -> POST
    public CourseResponseDTO onboardNewCourse(CourseRequestDTO courseRequestDTO) {
        CourseEntity courseEntity = AppUtils.mapDTOToEntity(courseRequestDTO);
        CourseEntity entity=null;
        try {
             entity = courseDao.save(courseEntity);
            CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(entity);
            courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
            return courseResponseDTO;
        }catch (Exception exception){
            throw new CourseServiceBusinessException("onboardNewCourse service method failed..");
        }
    }

    //load all the course from Database  // GET
    public List<CourseResponseDTO> viewAllCourses() {
        Iterable<CourseEntity> courseEntities=null;
        try {
            courseEntities = courseDao.findAll();
            return StreamSupport.stream(courseEntities.spliterator(), false)
                    .map(AppUtils::mapEntityToDTO)
                    .collect(Collectors.toList());
        }
        catch (Exception exception){
            throw new CourseServiceBusinessException("viewAllCourses service method failed..");

        }


    }

    //filter course by course id //GET
    public CourseResponseDTO findByCourseId(Integer courseId) {
        CourseEntity courseEntity = courseDao.findById(courseId)
                .orElseThrow(() -> new CourseServiceBusinessException(courseId+" doesn't exist in system"));
        return AppUtils.mapEntityToDTO(courseEntity);
    }

    //delete course  //DELETE
    public void deleteCourse(int courseId) {
     courseDao.deleteById(courseId);
    }

    //update the course //PUT
    public CourseResponseDTO updateCourse(int courseId, CourseRequestDTO courseRequestDTO) {
        //get the existing object
        CourseEntity existingCourseEntity = courseDao.findById(courseId).orElse(null);
        //modify existing object with new value
        existingCourseEntity.setName(courseRequestDTO.getName());
        existingCourseEntity.setTrainerName(courseRequestDTO.getTrainerName());
        existingCourseEntity.setDuration(courseRequestDTO.getDuration());
        existingCourseEntity.setStartDate(courseRequestDTO.getStartDate());
        existingCourseEntity.setCourseType(courseRequestDTO.getCourseType());
        existingCourseEntity.setFees(courseRequestDTO.getFees());
        existingCourseEntity.setCertificateAvailable(courseRequestDTO.isCertificateAvailable());
        existingCourseEntity.setDescription(courseRequestDTO.getDescription());
        //save modified value
        CourseEntity updatedCourseEntity = courseDao.save(existingCourseEntity);
        return AppUtils.mapEntityToDTO(updatedCourseEntity);
    }


}
