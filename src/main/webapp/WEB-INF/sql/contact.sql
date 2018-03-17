#sql("find")
      SELECT u_id uid,c_id cid status FROM contact
        #for(x:cond)
           #(for.index == 0 ? "where": "and") #(x.key) #para(x.value)
        #end
#end

#sql("update")
    UPDATE contact SET status = 1 WHERE u_id = #para(0) AND c_id = #para(1)
#end

#sql("delete")
    DELETE  FROM contact WHERE  u_id = #para(0) AND c_id = #para(1) AND status = #para(2)
#end