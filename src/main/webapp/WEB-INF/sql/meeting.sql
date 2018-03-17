#sql("find")
      SELECT m_id mid, title, date, site, restrict FROM meeting
        #for(x:cond)
           #(for.index == 0 ? "where": "and") #(x.key) #para(x.value)
        #end
#end

#sql("findList")
      SELECT m_id mid, title FROM meeting
        #for(x:cond)
           #(for.index == 0 ? "where": "and") #(x.key) #para(x.value)
        #end
#end