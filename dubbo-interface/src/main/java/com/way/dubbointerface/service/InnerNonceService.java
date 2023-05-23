package com.way.dubbointerface.service;

/**
 * @author Way
 */
public interface InnerNonceService {
    /**
     * 获取一个新的nonce
     * @return nonce
     */
    String getNonce();

    /**
     * 删除当前nonce
     * @param nonce
     * @return false - 当nonce不存在 或 已被使用
     *         true - 成功删除后
     */
    Boolean removeNonce(String nonce);
}
