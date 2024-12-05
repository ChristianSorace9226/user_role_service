package it.nesea.albergo.utente_ruolo.mapper;

import it.nesea.albergo.utente_ruolo.dto.UtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;
import it.nesea.albergo.utente_ruolo.model.entity.Utente;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UtenteMapper {

    CreaUtenteDto toDto(Utente utente);

    Utente toEntity(CreaUtenteDto creaUtenteDto);

    Utente dtoToEntity(UtenteDto utenteDto);

    UtenteDto entityToDto(Utente utente);
}
