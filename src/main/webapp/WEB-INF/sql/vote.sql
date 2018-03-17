#sql("findResult")
    SELECT v_id ,u_id FROM voting_result
      #for(x:cond)
          #(for.index == 0 ? "where": "and") #(x.key) #para(x.value)
      #end
#end
