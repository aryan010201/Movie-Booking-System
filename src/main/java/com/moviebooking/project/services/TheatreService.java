package com.moviebooking.project.services;

import com.moviebooking.project.Payload.DTOs.TheatreDTO;
import com.moviebooking.project.Payload.Response.TheatreResponse;



public interface TheatreService {
    TheatreDTO createTheatre(TheatreDTO theatreDTO);
    TheatreResponse getAllTheatres(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    TheatreDTO getTheatreById(Long theatreId);
    TheatreDTO getTheatreByName(String theatreName);
    TheatreResponse getTheatresByCity(String city,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    TheatreResponse getTheatresByState(String state,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    TheatreDTO updateTheatre(Long theatreId,TheatreDTO theatreDTO);
    TheatreDTO deleteTheatre(Long theatreId);
}
