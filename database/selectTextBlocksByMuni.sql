﻿SELECT blockid, blockcategory_catid, muni_municode, blockname, blocktext, categoryid, cateogrytitle
  FROM public.textblock INNER JOIN public.textblockcategory 
  ON textblockcategory.categoryid=textblock.blockcategory_catid
  where muni_municode=;
