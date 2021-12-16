package vn.elca.training.common.service;

import ch.vd.technical.esb.EsbMessage;

/**
 * @author vlp
 */
public interface IEsbService {
    boolean sendMessage(EsbMessage message);
    boolean receiveMessage(EsbMessage message);
}
