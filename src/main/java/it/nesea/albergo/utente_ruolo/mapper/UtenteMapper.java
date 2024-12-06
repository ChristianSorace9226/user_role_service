package it.nesea.albergo.utente_ruolo.mapper;

import it.nesea.albergo.utente_ruolo.dto.UtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.ModUtenteDto;
import it.nesea.albergo.utente_ruolo.model.entity.Ruolo;
import it.nesea.albergo.utente_ruolo.model.entity.Utente;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UtenteMapper {

    @Mapping(source = "ruolo", target = "idRuolo")
    ModUtenteDto toModUtenteDto(Utente utente);

    Utente fromModUtenteDtoToUtente(ModUtenteDto creaUtenteDto);

    Utente fromCreaUtenteDtoToEntity(CreaUtenteDto creaUtenteDto);

    Utente dtoToEntity(UtenteDto utenteDto);

    @Mapping(source = "ruolo", target = "idRuolo")
    UtenteDto entityToDto(Utente utente);

    default Integer mapRoleToRoleId(Ruolo ruolo) {
        return ruolo.getId();
    }

}
