#sql("findList")
      SELECT r_id rid,name,m_id mid  FROM request
        #for(x:cond)
           #(for.index == 0 ? "where": "and") #(x.key) #para(x.value)
        #end
#end

#sql("find")
      SELECT u_id uid, m_id mid,name,tel,remark,status  FROM request
        #for(x:cond)
           #(for.index == 0 ? "where": "and") #(x.key) #para(x.value)
        #end
#end
