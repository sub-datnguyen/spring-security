package vn.elca.training.business.basicdata.service;

import ch.vd.technical.esb.EsbMessage;
import vn.elca.training.entity.EnginEntity;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface IEnginService {

    List<EnginEntity> createOrUpdateEnginByXml(InputStream inputStream, EsbMessage esbMessage);

    Optional<EnginEntity> getEnginByNumeroIdentification(Long numeroIdentification);

    EnginEntity saveEngin(EnginEntity enginEntity);
}
