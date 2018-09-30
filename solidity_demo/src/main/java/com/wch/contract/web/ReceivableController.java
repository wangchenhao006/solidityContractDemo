package com.wch.contract.web;


import com.wch.contract.response.BaseResult;
import com.wch.contract.service.IReceivable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/receivableContract")
@Api(value = "应收账单合约练习", description = "应收账单合约练习")
@Slf4j
public class ReceivableController {

    @Autowired
    private IReceivable receivableService;


    @PostMapping("/createAccount")
    @ApiOperation(value = "创建账户", notes = "创建账户")
    public BaseResult accountToBlockChain(
            @ApiParam(value = "公司名称") @RequestParam(value = "companyName") String companyName,
            @ApiParam(value = "统一社会信用代码") @RequestParam(value = "companySocialcreditCode") String companySocialcreditCode,
            @ApiParam(value = "通讯地址") @RequestParam(value = "companyContactAddress") String companyContactAddress,

            @ApiParam(value = "账户类型 1:标准企业") @RequestParam(value = "accountType") int accountType) throws Exception {

        BaseResult baseResult = receivableService.createAccountInvoke(companyName, companySocialcreditCode, companyContactAddress, accountType);
        return baseResult;
    }

    /**
     * 应收账单列表
     */
    @ApiOperation(value = "获取应收账单列表", notes = "获取应收账单列表")
    @GetMapping("/listReceivable")
    @ResponseBody
    public BaseResult receivableList(
            @RequestParam String tokenUserId,
            @ApiParam(value = "页号", required = true) @RequestParam Integer pageNumber,
            @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize
    ) throws Exception {
        BaseResult baseResult = receivableService.receivableList(tokenUserId, pageNumber, pageSize);
        return baseResult;
    }

    /**
     * 详情
     */
    @ApiOperation(value = "获取应收账单详情", notes = "获取应收账单详情")
    @GetMapping("/infoReceivable")
    @ResponseBody
    public BaseResult receivableInfo(
            @RequestParam String tokenUserId,
            @ApiParam(value = "账单编号", required = true) @RequestParam String receivableNum

    ) throws Exception {
        BaseResult baseResult = receivableService.receivableInfo(tokenUserId, receivableNum);
        return baseResult;
    }

    @ApiOperation(value = "签发账单",notes = "签发账单")
    @GetMapping("/createReceivable")
    @ResponseBody
    public BaseResult receivableCreate(
            @RequestParam String tokenUserId,
            @ApiParam(value = "接收方id",required = true) @RequestParam String toId,
            @ApiParam(value = "面值",required = true) @RequestParam String paValue,
            @ApiParam(value = "有效期",required = true) @RequestParam int validUntil

            )throws Exception{
        return receivableService.createReceivable(tokenUserId,toId,paValue,validUntil);
    }

    @ApiOperation(value = "转发账单",notes = "转发账单")
    @GetMapping("/transferReceivable")
    @ResponseBody
    public BaseResult receivableTransfer(
            @RequestParam String tokenUserId,
            @ApiParam(value = "接收方id",required = true) @RequestParam String toId,
            @ApiParam(value = "账单编号",required = true) @RequestParam String receivableNum

    )throws Exception{
        return receivableService.transferReceivable(tokenUserId,toId,receivableNum);
    }

    @ApiOperation(value = "合约升级",notes = "合约升级")
    @GetMapping("/updateContract")
    @ResponseBody
    public BaseResult updateContract(
            @RequestParam String tokenUserId
    )throws Exception{
        return receivableService.contractUpdate(tokenUserId);
    }

//    @GetMapping("/accountInfo")
//    @ApiOperation(value = "账户信息查询", notes = "账户信息查询")
//    public BaseResult accountInfo(@ApiParam(value = "用户id") @RequestParam(value = "tokenUserId") String tokenUserId) throws Exception {
//        BaseResult baseResult = ReceivableContractInvoke.accountInfo(tokenUserId);
//        return baseResult;
//    }




}
