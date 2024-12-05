package it.nesea.albergo.utente_ruolo.mapper;

import it.nesea.albergo.utente_ruolo.dto.RuoloDTO;
import it.nesea.albergo.utente_ruolo.dto.request.CreaRuoloRequest;
import it.nesea.albergo.utente_ruolo.model.entity.Ruolo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class RuoloMapper {
    public abstract RuoloDTO toRuoloDTO(Ruolo ruolo);

    public abstract Ruolo toRuolo(RuoloDTO ruoloDTO);

    public abstract Ruolo requestToEntity(CreaRuoloRequest request);
}
