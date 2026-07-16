package com.moviebooking.project.services;

import com.moviebooking.project.Payload.DTOs.BookingDTO;
import com.moviebooking.project.exception.APIException;
import com.moviebooking.project.exception.ResourceNotFoundException;
import com.moviebooking.project.model.*;
import com.moviebooking.project.repository.BookingRepository;
import com.moviebooking.project.repository.ShowRepository;
import com.moviebooking.project.repository.ShowSeatRepository;
import com.moviebooking.project.request.BookingRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    ShowRepository showRepository;

    @Autowired
    ShowSeatRepository showSeatRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BookingRepository bookingRepository;



    @Override
    @Transactional
    public String CreateBooking(BookingDTO bookingDTO) {

        Booking booking=modelMapper.map(bookingDTO, Booking.class);
        Show  show = showRepository.findById(bookingDTO.getShowId()).
                orElseThrow(()-> new ResourceNotFoundException("show","showId",bookingDTO.getShowId()));
        List<Long> showSeatIds = bookingDTO.getShowSeat();

        List<ShowSeat> showSeats=showSeatRepository.findAllById(showSeatIds);
        if(showSeatIds.size() != showSeats.size()){
            throw new APIException("Some seat ids are invalid.");
        }

        List<Long> unavailableSeats = new ArrayList<>();

        for (ShowSeat seat : showSeats) {

            if (seat.getSeatStatus() != SeatStatus.Unlocked) {
                unavailableSeats.add(seat.getShowSeatId());
            }
        }

        if (!unavailableSeats.isEmpty()) {
            return unavailableSeats.toString();
        }
        booking.setShow(show);
        booking.setShowSeats(showSeats);
        booking.setPaymentType(bookingDTO.getPaymentType()); // will add payment type in request
        booking.setTotalPrice(200.00); //will add calculation logic later
        booking.setBookingDate(LocalDate.now());
        booking.setBookingTime(LocalTime.now());
        bookingRepository.save(booking);
        return "Booking Completed Successfully";
    }

    @Override
    public List<Booking> BookingHistory(Long userId) {
        List<Booking> bookingList=bookingRepository.findWhereUserId(userId);
        return bookingList;
    }

    @Override
    public String CancelBooking(Long bookingId) {
        Booking booking=bookingRepository.findById(bookingId)
                .orElseThrow(()-> new ResourceNotFoundException("booking","bookingId",bookingId));
        if (booking.getBookingDate().equals(LocalDate.now())) {

            Duration duration = Duration.between(
                    booking.getBookingTime(),
                    LocalTime.now()
            );

            if (duration.toHours() <= 3) {
                bookingRepository.deleteById(bookingId);
            }
            else{
                throw new APIException("Now Booking can not be cancelled.");
            }
        }
        else{
            throw new APIException("Now Booking can not be cancelled.");
        }

        return "Booking has been cancelled.";
    }
}
