package cn.edu.xmu.oomall.sfexpress.controller;

import cn.edu.xmu.javaee.core.util.SnowFlakeIdWorker;
import cn.edu.xmu.oomall.sfexpress.controller.dto.CargoDetailsDTO;
import cn.edu.xmu.oomall.sfexpress.controller.dto.ContactInfoListDTO;
import cn.edu.xmu.oomall.sfexpress.exception.SFErrorCodeEnum;
import cn.edu.xmu.oomall.sfexpress.controller.vo.*;
import cn.edu.xmu.oomall.sfexpress.service.SfexpressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟顺丰控制器
 * @author Zhouzhe Fan
 * dgn3-004-fzz
 */
//@Validated
@RestController
@RequestMapping(value = "/internal/sf", produces = "application/json;charset=UTF-8")
public class SFExpressController {

    private final SfexpressService sfexpressService;

    @Autowired
    public SFExpressController(SfexpressService sfexpressService) {
        this.sfexpressService = sfexpressService;
    }

    @PostMapping("/")
    public SFResponseVo SFServiceAdapter(@Valid @RequestBody SFPostRequestVo<Object> sfPostRequestVo) throws JsonProcessingException {
        String serviceCode = sfPostRequestVo.getServiceCode();
        String partnerID = sfPostRequestVo.getPartnerID();
        String requestID = sfPostRequestVo.getRequestID();
        String timestamp = sfPostRequestVo.getTimestamp();
        String msgDigest = sfPostRequestVo.getMsgDigest();
        ObjectMapper objectMapper = new ObjectMapper();
        String msgDataJson = objectMapper.writeValueAsString(sfPostRequestVo.getMsgData());

        switch (serviceCode) {
            case "EXP_RECE_CREATE_ORDER":
                // 创建返回对象
                SFResponseVo<PostCreateOrderRetVo> postCreateOrderVoSFResponseVo = new SFResponseVo<>();
                postCreateOrderVoSFResponseVo.setApiResultCode("A1000");
                postCreateOrderVoSFResponseVo.setApiErrorMsg("");
                postCreateOrderVoSFResponseVo.setApiResponseID(requestID);
                ApiResultData<PostCreateOrderRetVo> postCreateOrderRetVoApiResultData = new ApiResultData<>();
                // 参数校验
                PostCreateOrderVo createOrderVo = objectMapper.readValue(msgDataJson, PostCreateOrderVo.class);
                List<ContactInfoListDTO> contactInfoList = createOrderVo.getContactInfoList();
                postCreateOrderRetVoApiResultData.setSuccess(false); // 先设成false，如果校验全通过了就改成true
                if (contactInfoList.size() < 2) {
                    postCreateOrderRetVoApiResultData.setErrorCode(SFErrorCodeEnum.E1010.getErrorCodeString());
                    postCreateOrderRetVoApiResultData.setErrorMsg(SFErrorCodeEnum.E1010.getErrorDescAndAdvice());
                    postCreateOrderVoSFResponseVo.setApiResultData(postCreateOrderRetVoApiResultData);
                    return postCreateOrderVoSFResponseVo;
                }
                for (ContactInfoListDTO contactInfo : contactInfoList) {
                    if (contactInfo.getContactType() == 1) {
                        if (StringUtils.isBlank(contactInfo.getAddress())) {
                            postCreateOrderRetVoApiResultData.setErrorCode(SFErrorCodeEnum.E1010.getErrorCodeString());
                            postCreateOrderRetVoApiResultData.setErrorMsg(SFErrorCodeEnum.E1010.getErrorDescAndAdvice());
                            postCreateOrderVoSFResponseVo.setApiResultData(postCreateOrderRetVoApiResultData);
                            return postCreateOrderVoSFResponseVo;
                        }
                        if (StringUtils.isBlank(contactInfo.getContact())) {
                            postCreateOrderRetVoApiResultData.setErrorCode(SFErrorCodeEnum.E1011.getErrorCodeString());
                            postCreateOrderRetVoApiResultData.setErrorMsg(SFErrorCodeEnum.E1011.getErrorDescAndAdvice());
                            postCreateOrderVoSFResponseVo.setApiResultData(postCreateOrderRetVoApiResultData);
                            return postCreateOrderVoSFResponseVo;
                        }
                        if (StringUtils.isBlank(contactInfo.getMobile())) {
                            postCreateOrderRetVoApiResultData.setErrorCode(SFErrorCodeEnum.E1012.getErrorCodeString());
                            postCreateOrderRetVoApiResultData.setErrorMsg(SFErrorCodeEnum.E1012.getErrorDescAndAdvice());
                            postCreateOrderVoSFResponseVo.setApiResultData(postCreateOrderRetVoApiResultData);
                            return postCreateOrderVoSFResponseVo;
                        }
                    } else if (contactInfo.getContactType() == 2) {
                        if (StringUtils.isBlank(contactInfo.getAddress())) {
                            postCreateOrderRetVoApiResultData.setErrorCode(SFErrorCodeEnum.E1014.getErrorCodeString());
                            postCreateOrderRetVoApiResultData.setErrorMsg(SFErrorCodeEnum.E1014.getErrorDescAndAdvice());
                            postCreateOrderVoSFResponseVo.setApiResultData(postCreateOrderRetVoApiResultData);
                            return postCreateOrderVoSFResponseVo;
                        }
                        if (StringUtils.isBlank(contactInfo.getContact())) {
                            postCreateOrderRetVoApiResultData.setErrorCode(SFErrorCodeEnum.E1015.getErrorCodeString());
                            postCreateOrderRetVoApiResultData.setErrorMsg(SFErrorCodeEnum.E1015.getErrorDescAndAdvice());
                            postCreateOrderVoSFResponseVo.setApiResultData(postCreateOrderRetVoApiResultData);
                            return postCreateOrderVoSFResponseVo;
                        }
                        if (StringUtils.isBlank(contactInfo.getMobile())) {
                            postCreateOrderRetVoApiResultData.setErrorCode(SFErrorCodeEnum.E1016.getErrorCodeString());
                            postCreateOrderRetVoApiResultData.setErrorMsg(SFErrorCodeEnum.E1016.getErrorDescAndAdvice());
                            postCreateOrderVoSFResponseVo.setApiResultData(postCreateOrderRetVoApiResultData);
                            return postCreateOrderVoSFResponseVo;
                        }
                    }
                }

                postCreateOrderRetVoApiResultData.setSuccess(true);
                postCreateOrderRetVoApiResultData.setErrorCode("S0000");
                postCreateOrderRetVoApiResultData.setErrorMsg(null);

                PostCreateOrderRetVo newOrderVo = sfexpressService.createOrder(createOrderVo);
                postCreateOrderRetVoApiResultData.setMsgData(newOrderVo);
                postCreateOrderVoSFResponseVo.setApiResultData(postCreateOrderRetVoApiResultData);
                return postCreateOrderVoSFResponseVo;

            case "EXP_RECE_SEARCH_ORDER_RESP":
                PostSearchOrderVo searchOrderVo = objectMapper.readValue(msgDataJson, PostSearchOrderVo.class);
                PostSearchOrderRetVo orderVo = sfexpressService.searchOrder(searchOrderVo);
                SFResponseVo<PostSearchOrderRetVo> postSearchOrderRetVoSFResponseVo = new SFResponseVo<>();
                postSearchOrderRetVoSFResponseVo.setApiResultCode("A1000");
                postSearchOrderRetVoSFResponseVo.setApiErrorMsg("");
                postSearchOrderRetVoSFResponseVo.setApiResponseID(requestID);
                ApiResultData<PostSearchOrderRetVo> postSearchOrderRetVoApiResultData = new ApiResultData<>();
                postSearchOrderRetVoApiResultData.setSuccess(true);
                postSearchOrderRetVoApiResultData.setErrorCode("S0000");
                postSearchOrderRetVoApiResultData.setErrorMsg(null);
                postSearchOrderRetVoApiResultData.setMsgData(orderVo);
                postSearchOrderRetVoSFResponseVo.setApiResultData(postSearchOrderRetVoApiResultData);
                return postSearchOrderRetVoSFResponseVo;

            case "EXP_RECE_SEARCH_ROUTES":
                PostSearchRoutesVo searchRoutesVo = objectMapper.readValue(msgDataJson, PostSearchRoutesVo.class);
                PostSearchRoutesRetVo route = sfexpressService.searchRoutes(searchRoutesVo);
                SFResponseVo<PostSearchRoutesRetVo> postSearchRoutesRetVoSFResponseVo = new SFResponseVo<>();
                postSearchRoutesRetVoSFResponseVo.setApiResultCode("A1000");
                postSearchRoutesRetVoSFResponseVo.setApiResponseID(requestID);
                postSearchRoutesRetVoSFResponseVo.setApiErrorMsg("");
                ApiResultData<PostSearchRoutesRetVo> postSearchRoutesRetVoApiResultData = new ApiResultData<>();
                postSearchRoutesRetVoApiResultData.setSuccess(true);
                postSearchRoutesRetVoApiResultData.setErrorCode("S0000");
                postSearchRoutesRetVoApiResultData.setErrorMsg(null);
                postSearchRoutesRetVoApiResultData.setMsgData(route);
                postSearchRoutesRetVoSFResponseVo.setApiResultData(postSearchRoutesRetVoApiResultData);
                return postSearchRoutesRetVoSFResponseVo;

            case "EXP_RECE_UPDATE_ORDER":
                PostUpdateOrderVo updateOrderVo = objectMapper.readValue(msgDataJson, PostUpdateOrderVo.class);
                PostUpdateOrderRetVo updateOrderRetVo = sfexpressService.updateOrder(updateOrderVo);
                SFResponseVo<PostUpdateOrderRetVo> postUpdateOrderRetVoSFResponseVo = new SFResponseVo<>();
                postUpdateOrderRetVoSFResponseVo.setApiResultCode("A1000");
                postUpdateOrderRetVoSFResponseVo.setApiResponseID(requestID);
                postUpdateOrderRetVoSFResponseVo.setApiErrorMsg("");
                ApiResultData<PostUpdateOrderRetVo> postUpdateOrderRetVoApiResultData = new ApiResultData<>();
                postUpdateOrderRetVoApiResultData.setSuccess(true);
                postUpdateOrderRetVoApiResultData.setErrorCode("S0000");
                postUpdateOrderRetVoApiResultData.setErrorMsg(null);
                postUpdateOrderRetVoApiResultData.setMsgData(updateOrderRetVo);
                postUpdateOrderRetVoSFResponseVo.setApiResultData(postUpdateOrderRetVoApiResultData);
                return postUpdateOrderRetVoSFResponseVo;

            case "COM_RECE_CLOUD_PRINT_WAYBILLS":
                PostPrintWaybillsVo printWaybillsVo = objectMapper.readValue(msgDataJson, PostPrintWaybillsVo.class);
                List<PostPrintWaybillsRetVo.ObjDTO.FilesDTO> filesDTOList = new ArrayList<>();
                for (PostPrintWaybillsVo.DocumentsDTO document : printWaybillsVo.getDocuments()) {
                    PostPrintWaybillsRetVo.ObjDTO.FilesDTO filesDTO = new PostPrintWaybillsRetVo.ObjDTO.FilesDTO();
                    filesDTO.setWaybillNo(document.getMasterWaybillNo());
                    filesDTOList.add(filesDTO);
                }
                PostPrintWaybillsRetVo printWaybillsRetVo = new PostPrintWaybillsRetVo();
                PostPrintWaybillsRetVo.ObjDTO objDTO = new PostPrintWaybillsRetVo.ObjDTO();
                objDTO.setFiles(filesDTOList);
                printWaybillsRetVo.setObj(objDTO);

                SFResponseVo<PostPrintWaybillsRetVo> postPrintWaybillsRetVoSFResponseVo = new SFResponseVo<>();
                postPrintWaybillsRetVoSFResponseVo.setApiResultCode("A1000");
                postPrintWaybillsRetVoSFResponseVo.setApiResponseID(requestID);
                postPrintWaybillsRetVoSFResponseVo.setApiErrorMsg("");
                ApiResultData<PostPrintWaybillsRetVo> searchRoutesRetVoApiResultData = new ApiResultData<>();
                searchRoutesRetVoApiResultData.setSuccess(true);
                searchRoutesRetVoApiResultData.setErrorCode("S0000");
                searchRoutesRetVoApiResultData.setErrorMsg(null);
                searchRoutesRetVoApiResultData.setMsgData(printWaybillsRetVo);
                postPrintWaybillsRetVoSFResponseVo.setApiResultData(searchRoutesRetVoApiResultData);
                return postPrintWaybillsRetVoSFResponseVo;

            default:
                SFResponseVo sfResponseVo = new SFResponseVo<>();
                sfResponseVo.setApiErrorMsg("服务代码不存在");
                sfResponseVo.setApiResponseID(requestID);
                sfResponseVo.setApiResultCode("A1001");
                return sfResponseVo;
        }
    }
}
