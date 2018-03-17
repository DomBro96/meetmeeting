#sql("find")
    SELECT u_id uid,username,account,icon FROM user
      #for(x:cond)
          #(for.index == 0 ? "where": "and") #(x.key) #para(x.value)
      #end
#end

#sql("findByLike")
    SELECT u_id uid,username,icon FROM user
    WHERE
    account LIKE #para(term) OR username LIKE #para(term)
#end

#sql("findList")
    SELECT u_id uid,username,icon FROM user
      #for(x:cond)
          #(for.index == 0 ? "where": "and") #(x.key) #para(x.value)
      #end
#end

