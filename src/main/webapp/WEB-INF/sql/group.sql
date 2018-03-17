#sql("find")
      SELECT g_id gid,m_id mid,admin conferee FROM meetmeeting.group
        #for(x:cond)
           #(for.index == 0 ? "where": "and") #(x.key) #para(x.value)
        #end
#end