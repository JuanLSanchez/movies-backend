package es.juanlsanchez.movies.compraentradas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import es.juanlsanchez.movies.compraentradas.dto.TimeDTO;
import es.juanlsanchez.movies.domain.Time;

@Mapper(componentModel = "spring")
public interface TimeDTOMapper {

  @Mappings({@Mapping(target = "id", ignore = true), @Mapping(target = "cinema", ignore = true),
      @Mapping(target = "movie", ignore = true), @Mapping(target = "timeHistories", ignore = true)})
  public Time toDomainTime(TimeDTO timeDTO);

}
