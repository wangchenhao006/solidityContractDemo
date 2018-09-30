//应收账款
contract ReceivableContract {
    enum State {
        Created,
        Locked,
        Inactive
    }
    //超级账号
    address superAddr;

    //账户信息
    mapping(address => Account) public Accounts;
    //操作历史
    //mapping (bytes32 => Operation) ctrIvkTxHash2Operation;
    //应收账款信息  主键,由账单编号加业务流水组成
    mapping(bytes32 => Receivable)  Receivables;
    address[] AccountAddrs;
    bytes32[] ReceivableNums;


    //用户结构体
    struct Account {
        //企业名称
        bytes companyName;
        //企业区块链地址
        address companyBCAddr;
        //统一社会信用代码
        bytes companySocialcreditCode;
        //企业通讯地址
        bytes companyContactAddress;

    }

    //应收账款
    struct Receivable {
        //应收款编号
        bytes32 receivableNum;
        //面值
        bytes32 parValue;
        //当前持有人
        address ownerAddr;
        //上一手持有人
        address lastOwnerAddr;
        //有效期
        uint validUntil;
    }

    function ReceivableContract() {
        Account account = Accounts[msg.sender];
        superAddr = msg.sender;

    }

    //签发功能，也就是创建应收账款
    function createReceivable(address to, bytes32 parValue, bytes32 receivableNum, uint validUntil) returns(bytes32) {
        Account sender = Accounts[msg.sender];
        //不允许签发给自己
        if (to == msg.sender){
            return "000001";
        }
        Receivable receivable = Receivables[receivableNum];
        receivable.ownerAddr = to;
        receivable.lastOwnerAddr = msg.sender;
        receivable.parValue = parValue;
        //todo 设置仓单有效期
        receivable.validUntil = validUntil;
        ReceivableNums.push(receivable.receivableNum) -1;
        return "000000";
    }

    //转让功能
    function transferReceivable(address to, bytes32 receivableNum, uint nowTime) returns(bytes32) {
        Account sender = Accounts[msg.sender];
        //不允许转给自己
        if (to == msg.sender){
            return "000001";
        }
        //todo 验证转出账户有效性

        Receivable receivable = Receivables[receivableNum];
        // 判断仓单有没有过期,时间可能有问题
        if (receivable.validUntil <= nowTime){
            return "000002";
        }

        receivable.ownerAddr = to;
        receivable.lastOwnerAddr = msg.sender;
        return "000000";
    }

    //创建区块链账号
    function createAccount(bytes companyName, bytes companySocialcreditCode, bytes companyContactAddress, address accountAddr, uint accoutType) returns(bytes32) {
        //todo 判断创建人有没有足够的Token
        if (msg.sender != superAddr) {
            //不是超级账户，不能创建账户
            return "000001";
        }
        Account account = Accounts[accountAddr];
        account.companyName = companyName;
        account.companyBCAddr = accountAddr;
        account.companySocialcreditCode = companySocialcreditCode;
        account.companyContactAddress = companyContactAddress;
        AccountAddrs.push(accountAddr) -1;
        return "000000";
    }

    //每个人可以查看到自己持有的应收账款列表，包含应收款编号，面值2个字段
    function listReceivable() constant returns (bytes32[], bytes32[]){
        uint i = 0;
        uint j = 0;
        bytes32[] memory nums;
        bytes32[] memory parValues;
        for(i = 0; i < ReceivableNums.length; i++){
            bytes32 temp = ReceivableNums[i];
            if( Receivables[temp].ownerAddr == msg.sender){
                nums[j] = Receivables[temp].receivableNum;
                parValues[j++] = Receivables[temp].parValue;
            }
        }
        return (nums, parValues);
    }

    //根据应收编号查应收款详情，包含：应收款编号、面值、当前持有人、上一手持有人、有效期
    function getReceivableInfo(bytes32 receivableNum) returns (bytes32,bytes32,bytes,bytes,uint){
        Receivable receivable = Receivables[receivableNum];
        if(receivable.receivableNum == ""){
            return ;
        }
        Account owner = Accounts[receivable.ownerAddr];
        Account lastOwner = Accounts[receivable.lastOwnerAddr];
        return (receivable.receivableNum, receivable.parValue, owner.companyName, lastOwner.companyName, receivable.validUntil);
    }

    //查询企业信息
    function getAccountInfo() constant returns (bytes, bytes, bytes) {
        Account account = Accounts[msg.sender];
        return (account.companyName, account.companyContactAddress,
        account.companySocialcreditCode);
    }

    function validAccount(address to) returns(bool){
        Account account = Accounts[to];
        if(to != 0x0){
            return true;
        }
        return false;
    }





}