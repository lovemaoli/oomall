package cn.edu.xmu.oomall.service;
import cn.edu.xmu.oomall.service.mapper.po.ServiceOrderPo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceOrderPoTest {

    private ServiceOrderPo serviceOrderPo;

    @BeforeEach
    public void setup() {
        serviceOrderPo = new ServiceOrderPo();
    }

    @Test
    public void setIdAndGetIdWorkCorrectly() {
        Long expectedId = 1L;
        serviceOrderPo.setId(expectedId);
        assertEquals(expectedId, serviceOrderPo.getId());
    }

    @Test
    public void setStatusAndGetStatusWorkCorrectly() {
        Integer expectedStatus = 1;
        serviceOrderPo.setStatus(expectedStatus);
        assertEquals(expectedStatus, serviceOrderPo.getStatus());
    }

    @Test
    public void setTypeAndGetTypeWorkCorrectly() {
        Integer expectedType = 1;
        serviceOrderPo.setType(expectedType);
        assertEquals(expectedType, serviceOrderPo.getType());
    }

    @Test
    public void setAddressAndGetAddressWorkCorrectly() {
        String expectedAddress = "Test Address";
        serviceOrderPo.setAddress(expectedAddress);
        assertEquals(expectedAddress, serviceOrderPo.getAddress());
    }

    @Test
    public void setConsigneeAndGetConsigneeWorkCorrectly() {
        String expectedConsignee = "Test Consignee";
        serviceOrderPo.setConsignee(expectedConsignee);
        assertEquals(expectedConsignee, serviceOrderPo.getConsignee());
    }

    @Test
    public void setMobileAndGetMobileWorkCorrectly() {
        String expectedMobile = "Test Mobile";
        serviceOrderPo.setMobile(expectedMobile);
        assertEquals(expectedMobile, serviceOrderPo.getMobile());
    }

    @Test
    public void setServiceSnAndGetServiceSnWorkCorrectly() {
        Long expectedServiceSn = 1L;
        serviceOrderPo.setService_sn(expectedServiceSn);
        assertEquals(expectedServiceSn, serviceOrderPo.getService_sn());
    }

    @Test
    public void setDescriptionAndGetDescriptionWorkCorrectly() {
        String expectedDescription = "Test Description";
        serviceOrderPo.setDescription(expectedDescription);
        assertEquals(expectedDescription, serviceOrderPo.getDescription());
    }

    @Test
    public void setService_provider_nameAndGetService_provider_nameWorkCorrectly() {
        String expectedServiceProviderName = "Test ServiceProviderName";
        serviceOrderPo.setService_provider_name(expectedServiceProviderName);
        assertEquals(expectedServiceProviderName, serviceOrderPo.getService_provider_name());
    }

    @Test
    public void setService_provider_mobileAndGetService_provider_mobileWorkCorrectly() {
        String expectedServiceProviderMobile = "Test ServiceProviderMobile";
        serviceOrderPo.setService_provider_mobile(expectedServiceProviderMobile);
        assertEquals(expectedServiceProviderMobile, serviceOrderPo.getService_provider_mobile());
    }

    @Test
    public void setShop_idAndGetShop_idWorkCorrectly() {
        Long expectedShopId = 1L;
        serviceOrderPo.setShop_id(expectedShopId);
        assertEquals(expectedShopId, serviceOrderPo.getShop_id());
    }

    @Test
    public void setCustomer_idAndGetCustomer_idWorkCorrectly() {
        Long expectedCustomerId = 1L;
        serviceOrderPo.setCustomer_id(expectedCustomerId);
        assertEquals(expectedCustomerId, serviceOrderPo.getCustomer_id());
    }

    @Test
    public void setService_provider_idAndGetService_provider_idWorkCorrectly() {
        Long expectedServiceProviderId = 1L;
        serviceOrderPo.setService_provider_id(expectedServiceProviderId);
        assertEquals(expectedServiceProviderId, serviceOrderPo.getService_provider_id());
    }

    @Test
    public void setService_idAndGetService_idWorkCorrectly() {
        Long expectedServiceId = 1L;
        serviceOrderPo.setService_id(expectedServiceId);
        assertEquals(expectedServiceId, serviceOrderPo.getService_id());
    }

    @Test
    public void setOrder_item_idAndGetOrder_item_idWorkCorrectly() {
        Long expectedOrderItemId = 1L;
        serviceOrderPo.setOrder_item_id(expectedOrderItemId);
        assertEquals(expectedOrderItemId, serviceOrderPo.getOrder_item_id());
    }

    @Test
    public void setProduct_idAndGetProduct_idWorkCorrectly() {
        Long expectedProductId = 1L;
        serviceOrderPo.setProduct_id(expectedProductId);
        assertEquals(expectedProductId, serviceOrderPo.getProduct_id());
    }

    @Test
    public void setProduct_item_idAndGetProduct_item_idWorkCorrectly() {
        Long expectedProductItemId = 1L;
        serviceOrderPo.setProduct_item_id(expectedProductItemId);
        assertEquals(expectedProductItemId, serviceOrderPo.getProduct_item_id());
    }


}