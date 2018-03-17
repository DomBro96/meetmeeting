#sql("find")
      SELECT msg_id msgId,send_id sendId,type,message,send_date_time date,read FROM message
        #for(x:cond)
           #(for.index == 0 ? "where": "and") #(x.key) #para(x.value)
        #end
#end

#sql("findList")
      SELECT msg_id msgId,type,send_date_time date,read FROM message
        #for(x:cond)
           #(for.index == 0 ? "where": "and") #(x.key) #para(x.value)
        #end
#end