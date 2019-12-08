# future-storage
 future-storage为分布式文件存储系统，提供各种类型文件的上传、下载和删除功能接口，接口遵从Restful接口风格，下面是各接口详细介绍： 
 1，文件上传接口
 接口地址：http://127.0.0.1:8080/storage/file/upload
 访问类型：POST
 参数：file 上传的文件
 请求头信息： Content-Type:application/x-www-form-urlencoded 
             Authorization:1ffcfffb4fd848f2b3c7ee72eb05de74:VTGON3gXclzc36YHmx8TYym13XJBu9fMExXNMOLWNHVA4CCDpDxdvGcBW5V1ISXL
 通过文件上传接口每次可以上传一个文件，如果用户上传同一个文件，则最新的文件替换原来的文件。每个用户只能操作自己的文件。
 
 2，文件下载接口
 接口地址：http://127.0.0.1:8080/storage/file/download
 访问类型：POST
 请求头信息： Content-Type:application/x-www-form-urlencoded 
             Authorization:1ffcfffb4fd848f2b3c7ee72eb05de74:VTGON3gXclzc36YHmx8TYym13XJBu9fMExXNMOLWNHVA4CCDpDxdvGcBW5V1ISXL
 通过文件下载接口可以根据文件名称下载需要的文件，文件名称需要经过accessKey使用DES加密生成密文，再将密文放在URL中。每个用户只能下载自己的文件。
 
 3，文件删除接口
 接口地址：http://127.0.0.1:8080/storage/file/delete
 访问类型：POST
 请求头信息： Content-Type:application/x-www-form-urlencoded 
             Authorization:1ffcfffb4fd848f2b3c7ee72eb05de74:VTGON3gXclzc36YHmx8TYym13XJBu9fMExXNMOLWNHVA4CCDpDxdvGcBW5V1ISXL
 通过文件删除接口可以删除不需要的文件，文件名称需要经过accessKey使用DES加密生成密文，再将密文放在URL中。每个用户只能删除自己的文件。
 
 用户认证 Authorization请求头包含用户认证信息，内容为用户accessId:加密信息，加密信息为用户accessId、发送时间戳和accessKey拼接而成的字符串经过MD5加密后生成密文，再用发送时间戳和前面生成的密文使用accessKey使用DES加密生成最终的密文，accessKey需要用户保密，禁止外传。
