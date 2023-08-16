package com.devsuperior.uri2611.repositories;

import com.devsuperior.uri2611.dto.MovieDTO;
import com.devsuperior.uri2611.entities.Movie;
import com.devsuperior.uri2611.projections.repositories.MovieProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

  @Query(nativeQuery = true, value =
          "SELECT m.id, m.name " +
          "FROM movies m INNER JOIN genres ON m.id_genres = genres.id " +
          "WHERE upper(genres.description) = upper(:genreName)")
  List<MovieProjection> search1(String genreName);

  @Query(value =
          "SELECT new com.devsuperior.uri2611.dto.MovieDTO(m.id, m.name)" +
          "FROM Movie m " +
          "WHERE upper(m.genre.description) = upper(:genreName)")
  List<MovieDTO> search2(String genreName);

}
