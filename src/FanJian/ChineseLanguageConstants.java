package FanJian;

public class ChineseLanguageConstants {
  public static int ORG = 0;
  public static int TRANS = 1;
  public static String [] SPACE = {"¡¡", " "};
  public static String [] ENCLOSING_MARKS = { 
	    "¡°¡±£û£ý£Û£Ý¡¶¡·£¨£©¡®¡¯¡¸¡¹¡¾¡¿",
	    "\"\"{}[]<>()''\"\"[]"
  };
  public static String [] SENTENCE_SEPARATORS = { 
    "¡££¡£¿", 
    ".!?" 
  };
  public static String [] IN_SENTENCE__SEPARATORS = {
    "£º£»¡«£¬£¯£à£À£££¤£¥¡­¡­£¦£ª£­£½¡ª¡ª£«¡¢£ü",
    ":;~,/`@#$%^&*-=_+\\|0123456789"
  };
  public static String [] ALL_MARKS = {
    ENCLOSING_MARKS[0] + SENTENCE_SEPARATORS[0] + IN_SENTENCE__SEPARATORS[0],
    ENCLOSING_MARKS[1] + SENTENCE_SEPARATORS[1] + IN_SENTENCE__SEPARATORS[1]
  };

  public static int ENGLISH_START = 49;
  public static int ENGLISH_END =109;
  public static int CHINESE_START = 19968;
  public static int CHINESE_END = 40869;
  public static int JAPANESE_START = 12353;
  public static int JAPANESE_END = 12543;
  public static int KOREAN_START = 44032;
  public static int KOREAN_END = 55203;

  public static String SIMPLIFIED_CHARS = "ÍòÓë³ó×¨Òµ´Ô¶«Ë¿¶ªÁ½ÑÏÉ¥¸ö¸öãÜ·áÁÙÎªÀö¾ÙÄËÃ´ÒåÎÚÀÖÇÇÏ°ÏçÊéÂòÂÒÕùÓÚ¿÷ÔÆØ¨ÑÇ²úÄ¶Ç×ÙôÒÚ½öÆÍ´ÓÂØÂØ²Ö"
			+ "ÒÇÃÇ¼ÛÖÚÓÅ»ï»áØñÉ¡É¡Î°´«ÉËØöÂ×Ø÷Î±ØùÓÓÌåÓà·ðÓ¶ÙÝÅåÖ¶ÏÀÂÂ½ÄÕì²àÇÈ¿ëÙ­Ù¯Ù¶Ù±Ù²Á©Ù³¼óÐÞ¸©Õ®ÇãÙÌÍµÙÍÙÇ³¥"
			+ "ÙÎÙÏ´¢ÙÐ¶ù¿Ë¶ÒÙðµ³À¼¹ØÐË×ÈÑøÊÞÙæÄÚ¸Ô²áÐ´¾üÅ©Ú£¶¬·ëÙü³å³å¾ö¿ö¶³¾»Æà×¼Á¹Áè¼õ´ÕÁÝ¼¸·ïÙìÆ¾¿­³ö»÷ÛÊÔäÛ»»®"
			+ "ÁõÔò¸Õ´´É¾ÅÙ±ð±ð„iØÙ¹ÎÖÆ¹ôØÛØÜ¼Á¹Ð½£°þ¾çÊ£½ËÈ°°ìÎñÛ½¶¯Àø¾¢ÀÍÊÆÑ«ÛÂÛÃ„ÖÔÈØÐØÑÇøÒ½Ç§ÉýÉý»ªÐ­µ¥Âô²·Õ¼Â¬"
			+ "Â±Â±ÎÔÎÀÈ´¾íÚá³§ÌüÀúÀúÀ÷Ñ¹ÑáØÇ²ÞÀåÏáØÉÏÃ³ø¾ÇØËÏØ²Î…¥…¦Ë«·¢·¢±äÐðµþÖ»Ì¨Ì¨Ì¨Ò¶ºÅÌ¾Ì¾ß´ÓõÓõ³ÔºÏºÏµõºóÏò"
			+ "ÏòÏÅÂÀÂðßÄ¶ÖÌýÆôÎâ´ôß¼ß½Å»ß¿ßÂÔ±ßÃÇºÎØÖÜºÇºôÓ½ßÇÁüßÌßÐßå×ÉÔÛßÔÏÌÑÊºåßßÏìÑÆßÕßØßÙßÜ»©»©ßàßâßæÓ´´½ßé†y"
			+ "ßë†|ßïßð»½ßüßõØÄßùÄö†ª†®Ð¥Î¹Åçà¶à·à¿àÈÐêàÓÖöÎûààÔëàèÏùàë»ØÍÅÍÅÔ°À§´ÑÎ§àð¹úÍ¼Ô²Ê¥ÛÛ³¡Ö·Ûà»µ¿é¼áÌ³Ì³ÛÞ"
			+ "°ÓÎë·Ø×¹Â¢ÛâÛäÛäÀÝ¿ÑÛðÛÑµæÛëˆ™ˆ›ÛîÛñÛõÛ÷Û÷ÛöÛþÛûÇµ¶éµÌ‰GÜ­Ç½×³Éù¿Çºø‰×´¦±¸¸´¸´¹»·òÍ·¿ä¼Ð¶áÞÆÛ¼·Ü½±°Â"
			+ "ÄÌ¼é×±¸¾Âèåüåýæ£æ©½ªÂ¦æ«æ¬½¿æ®ÄïÓéæ´æµ‹OÓ¤æ¿ÉôæÁæÈæÉæÍæÖËïÑ§ÂÏÄþÄþ±¦Êµ³èÉóÏÜ¹¬¼Ò¿í±öÇÞ¶ÔÑ°µ¼ÊÙ½«¶û³¾"
			+ "³¢³¢Ò¢ÞÏÊ¬¾¡¾¡¾Ö¾Ö²ãŒÁÌë½ìÊôÂÅåðÓìËêÆñá«¸Úá­á®á°µºÑÒÑÒÁëÔÀá´¿ùNá»Ï¿iá½á¿ÂÍáÀáÁÕ¸áÉÂáÎáÐáÕáÛ¾Þ¹®"
			+ "ÛÏ±Ò²¼Ë§Ê¦àøÕÊÁ±ÖÄ´øÖ¡Ï¯°ïàüàýàþÃÝá¥¸É¸É¸É²¢ÐÒ¹ã×¯Çì´²Â®âÐ¿âÓ¦ÃíÅÓ·ÏâÖŽöâÞ¿ªÒìÆúÕÅÃÖÃÖÏÒåòÍäµ¯Ç¿Ç¿¹é"
			+ "µ±Â¼¦Ñå²ÊáÝ³¹Õ÷¾¶¾¶áâÓùÒäâãÖ¾ÓÇÄîâé»³Ì¬ËËâäâæâêâëÁ¯×Üí¡âøÁµºãÐô¿Ò¶ñ¶ñâúâûâýâüÄÕã¢ÔÃí¨Ðüã¥Ãõ¾ª¾å²Ò³Í"
			+ "±¹ã«²Ñµ¬¹ßÓúíªã³·ßã´Ô¸ÉåÉå‘\ãÀí¯ÀÁãÁí°ê§Ï·ê¨Õ½ÆÝê¯»§ÔúÆËÍÐ¿ÛÇ¤Ö´À©ÞÑÉ¨ÑïÑïÈÅÕÛ¸§Å×ÞÒ¿ÙÂÕÇÀ»¤±¨µÖµÖµ£"
			+ "¹ÕÄâÂ£¼ðÓµÀ¹Å¡²¦ÔñÆ´¹ÒÖ¿ÂÎ’¥ÎÎÌ¢Ð®ÄÓµ²µ²ÞØÕõ¼·»Ó’¦ÍìÀ¦À¦ÀÌËð¼ñ»»µ·¾ÝÄíÂ°ÞâÖÀµ§²ôÞèÞêÀ¿Þì²ó¸éÂ§½ÁËÑÐ¯"
			+ "ÉãÞó°ÚÒ¡±÷Ì¯Þü³ÅÄìß¢ß£ß¥ËÓÔÜµÐÁ²ÊýÕ«ìµ¶·Õ¶¶ÏÐýÆìÎÞ¾ÉÊ±¿õ•DÀ¥ê¼êÇÖç•oÏÔ½úÉ¹ÏþêÊÔÎêÍÔÝÅ¯êÓÇúÔýÊõÖìÆÓ»ú"
			+ "É±ÔÓÈ¨¸ËÌõÀ´Ñîè¿±­½ÜËÉ°å¼«¹¹¹¹èÈÊàÔæèÀèÅèÇÇ¹·ãèÉ¹ñÄûèßèÙÕ¤±êÕ»èÎèÐ¶°èÓèÝÀ¸Ê÷ÆÜÀõÑùºËèïèðèâèãèåµµèçÇÅ"
			+ "èëèí½°×®ÃÎ—ƒ—…¼ìèùèùé¢Àâèþé¤èüèýé¡ÍÖÂ¥é­é´éµé·Õ¥˜–¼÷éÄéÆÄ£ºáéÉÓ£éÍ³÷éÖéÚéÜéÝ»¶»¶ÐÀì£Å·Óû¼ßéâéä²Ðéæéç"
			+ "éééëÅ¹Òó»Ù»Ùì±±Ï±ÐÕ±ë§ëªÆøÇâë²ëµ»ã»ãººÎÛÌÀÐÚí³¹µÃ»Ã»ããÅ½Á¤ÂÙ²×›hãí»¦›mÕ´Ð¹·º·ºÅ¢×¢Àáí´ãñãòãøÐºÆÃÔóãþ"
			+ "½àÈ÷ÍÝä¤Ç³½¬½½ä¥›¸×Ç²âä«¼Ãä¯›º»ëä°Å¨ä±›»¿£Í¿Ó¿ÌÎÀÔäµÁ°ä¶ÎÐ›é»ÁµÓÈó½§ÕÇÉ¬µíÔ¨äË×ÕäÂ½¥äÅÓæäÉÉøÎÂÓÎÍåÊª"
			+ "À£½¦äÓœ¾ÁïÏªää¹öÖÍäÙäÙäÜÂúäÞÂËÀÄÂÐ±õÌ²œùÀìäíäëäìäòÎ«Ç±äó³ÎÀ½äþ±ôå°ÃðµÆÁéÔÖ²Óì¾Â¯Â¯ìÀì¿ìÁÅÚµãÁ¶Á¶³ãË¸"
			+ "ÀÃÌþÖòÑÌÑÌ·³ÉÕìÇ»âÌÌ½ýÈÈº¸º¸»ÀìËìâìÑÕÕìÎÑ¬ìÖÑàÑà°®Ò¯ë¹êóêóÇ£Îþ¶¿êñ×´áîáïÓÌ±·áóªAÄü¶ÀÏÁÊ¨áöÕøÓüáøÀêáý"
			+ "ÁÔâ¨â¤ÖíÃ¨â¬Ï×Ì¡çá«_«`Âêçâ»·ÏÖ«oçôçëçå·©çç«šçõÇòÀÅ¬QçöËöÇíÑþè¨è¯è¬è¶ÎÍÎÍê±µç»­³©î´³ëðÜÁÆÅ±ðÝÑñðß´¯·è"
			+ "ðåðâÖ¢Ó¸¾·Ñ÷ðéðì»¾ðï³Õ±Ôð÷¯}ðùðü±ñÌ±ñ«ñ¨ñ®Ñ¢ñ²ñ³°¨ÖåñäÕµÑÎ¼à¸ÇµÁÅÌÅÌÅÌíîíö±€Ì÷×ÅÕöíùíúÂ÷Öõ½Ãí¶·¯¿óí¸"
			+ "Âë×©×©íºÑâí¿íÂíÃÀù´¡³n¹èË¶íÌíÍíÍ³}³~È·¼ï°­íÓí×¼î¼îíÛíÞÁ×Àñµtìòìõµ»»öÙ÷Â»ìøÀëÍº¸ÑÇïÖÖÃØ»ý³Æ»à¶ŒïùË°öÕ"
			+ "ÎÈð£ÇîÇÔÇÏÒ¤´ÜÎÑ¿ú¿úñ¼ñÀÊú¾º¶ËóÆËñ±ÊóÈ¼ã¼ãÁýóÖÖþóÙÉ¸¹YóÝóã³ïÇ©Ç©¼ò¹‚¹ÜóåóæóêÂáóìóïóñÂ¨ÀºÀéóýô¥ÙáÀàôÌ"
			+ "ôÐôÏÔÁ·àÁ¸ôÖô×ÏµÏµ½ôÀÛôêæù¾ÀæúºìæûÏËæüÔ¼¼¶æýæþ¼ÍÈÒÎ³ç¡À€´¿ç¢É´¸ÙÄÉÀ×ÝÂÚ·×Ö½ÎÆ·ÄÀ‚ÀƒÅ¦ç£Ïßç¤ç¥ç¥ç¦Á·"
			+ "×éÉðÏ¸Ö¯ÖÕç§°íç¨ç©ÉÜÒï¾­çª°óÈÞÈÞ½áç«ÈÆÀ„ç¬»æ¸øÑ¤ç­Âç¾ø½ÊÍ³ç®ç¯¾îÐåÀ…ËçÌÐÌÐ¼Ìç°¼¨Ð÷ç±À†Ðøç²ç³´Âç´çµÉþ"
			+ "Î¬Ãàç·±Á³ñÀ‡ç¸ç¹×ÛÕÀçºÂÌ×ºç»ç¼ç½¼êÃåÀÂç¾ç¿¼©ÀˆÀˆçÀçÁç¶¶ÐçÂÀ‰çÃçÄ»ºµÞÂÆ±àçÅÔµçÆ¸¿çÈçÇ·ìÀŠçÉ²øçÊçËçÌçÍ"
			+ "çÎçÏçÐÓ§ËõçÑçÒçÓçÔÉÉçÕçÖç×çØçÙ½ÉçÚó¿ó¿ÍøÂÞ·£°Õî¼î¿ôÇÏÛÇÌÁ™Áš·­¿¼ñìñïËÊ³ÜÄôÁûÖ°ñ÷Áªñù´ÏËà³¦·ôëÈëÉÉöÖ×"
			+ "ÕÍÐ²µ¨±³Ê¤ºúºúëÊëËëÍëÖ½ºÂöëÚÔàÔàÆêÄÔÅ§Ùõ½ÅÍÑëáÁ³À°ëçÄNëñÄåëïëðÌÚë÷ÅHÖÂôªÓßÉáô¯½¢²Õôµ¼èÑÞÜ³ÒÕ½ÚØÂÜ¼Îß"
			+ "Â«Ü¿ÜÊÎ­ÜÂÜÈÜÉ²ÔÜÑËÕËÕÜÜÆ»·¶¾¥Ü×ÜàÜãÜä¼ë¾£¼öÇQ¼ÔÜéÜêÜñÜöÜùµ´µ´ÈÙ»çÜþÜýÓ«Ý¡Ý£Ý¥ÒñÝ¤Ý¦Ý§Ò©Ò©Ý°Ý¯À³Á«Ýª"
			+ "Ý«Ý²»ñ»ñÝµÓ¨ÝºÝ»È[ÂÜÓ©ÓªÝÓÏôÈø´ÐÝÛÝÞ½¯ÝäÃÉÃÉËòÀ¶¼»ÝñÝ÷ÝöÝëÃïÇ¾ÝüÝþ°ªÞ­ÔÌÞ´Þ»ÞºÌÙÂ²ÂÇÐé³æò°ò±Ê­ËäÏºò²"
			+ "Ê´ÒÏÒÏÂì²Ïòºò¹¹ÆòÃòÉÂùÕÝòÌòÍòÏòÓÍÉÎÏÀ¯òéÓ¬òå²õÐ«ò÷òîÎ…òýÏ]ÐÆÏÎÏÎ²¹±í³ÄÙò°ÀôÁôÁÐ„ÍàÏ®ÑB×°ñÉÑTñÍñÏ¿ãñÐ"
			+ "ñÚñÜñßÒ[¼û¹ÛÓ_¹æÃÙÊÓêèÀÀ¾õêéêêêëÓ`êìêíêîêïõü´¥ö£Ô€ÓþÌÜÚ¥¼Æ¶©¸¼ÈÏ¼¥Ú¦Ú§ÌÖÈÃÚ¨ÆýÑµÒéÑ¶¼Ç×š½²»äÚ©ÚªÑÈÚ«"
			+ "Ðí¶ïÂÛ×›ËÏ·íÉè·Ã¾÷Ö¤Ö¤Ú¬Ú­ÆÀ×çÊ¶×œÕ©ËßÕïÚ®Öß´ÊÚ°Ú¯×ÒëÚ±Ú²Ú³ÊÔÚ´Ê«ÚµÚ¶³ÏÖïÚ·»°µ®Ú¸Ú¹¹îÑ¯ÒèÚº¸ÃÏê²ïÚ»"
			+ "Ú¼×ž½ëÎÜÓïÚ½ÎóÚ¾ÓÕ»åÚ¿ËµËÐÚÀÇëÖîÚÁÅµ¶ÁÚÂ·Ì¿ÎÚÃÚÄË­ÚÅµ÷ÚÆÁÂ×»ÚÇÌ¸ÒêÄ±ÚÈµý»ÑÚÉÐ³ÚÊÚËÎ½ÚÌÚÍÚÎ²÷ÚÑÚÏÑèÚÐ"
			+ "ÃÕÚÒ× ÚÓÚÔÚÕÐ»Ò¥°ùÚÖÇ«Ú×½÷Ã¡ÚØÚÙÃýÌ·ÚÚÚÛÀ¾Æ×ÚÜÚÝÇ´ÚÞÚß¹ÈØk±´Õê¸ºÚO¹±²ÆÔðÏÍ°ÜÕË»õÖÊ··Ì°Æ¶±á¹ºÖü¹á·¡¼ú"
			+ "êÚêÛÌù¹óêÜ´ûÃ³·ÑºØêÝÔôêÞ¼Ö»ßêßÁÞÂ¸Ôß×ÊêàêáêäêâêãÉÞ¸³¶ÄêåÊêÉÍ´ÍÚPÚQâÙÅâêæÀµÚR×¸êç×¬ÈüØÓØÍÔÞÔÞÚSÔùÉÄÓ®"
			+ "¸ÓÚWÕÔ¸ÏÇ÷ôõõ»Ô¾õÄõÅõÈ¼ùÛQõÎõÏõÑõÒÓ»³ì×ÙõÙõÜõæõçõé´ÚõïõòÇû³µÔþ¹ìÐùÞaéí×ªéîÂÖÈíºäéïéðéñÖáéòéóéõéôéöé÷"
			+ "ÇáéøÔØéù½ÎÞbéúéû½Ïéü¸¨Á¾éý±²»Ô»Ô¹õéþÞcê¡ê¢ê£·ø¼­ÞdÊäàÎÔ¯Ï½Õ·ê¤ÕÞê¥´Ç±Ù±ç±è±ßÁÉ´ïÇ¨¹ýÂõÔË»¹Õâ½øÔ¶Î¥Á¬"
			+ "³ÙåÇµüåÉ¼£¼£ÊÊÑ¡Ñ·µÝåÎÂßÒÅÒ£µËÚ÷ÚùÓÊ×ÞÚþÁÚÓôÛ§Û£Û¦Ö£Û©ÛªÔÇµ¦ÔÍáNõ£½´Ëáõ¦õ§Äð²ÉÊÍÀïÀïâ ¼ø¼øöÇöÉîÅîÆÕë"
			+ "Õë¶¤îÈîÇîÉîÊÇ¥îËîÌè•·°µöîÍîÏè–îÎè—¸ÆîÐîÑ¶Û³®ÖÓÖÓÄÆ±µ¸ÖîÓîÔÔ¿Ô¿ÇÕ¾ûÎÙ¹³îÖîÕîØî×Å¥îÙîÚÇ®îÛÇ¯îÜ²§îÝîÞîß"
			+ "îàîá×êîâîã¼ØîäÓËÌú²¬ÁåîåÇ¦Ã­îæîçîèîéîêîëîìè™îíîîîïèšè›îðîòîôîóèœîõÍ­ÂÁîöî÷îøÕ¡îùÏ³îúîûèîüîýï¡îþï¢¸õ"
			+ "Ãúï£ï¤½ÂÒ¿²ù²ùï¥ï¦ï§Òøï¨Öýï©ÆÌÆÌèžïªï«Á´ï¬ÏúËøï®ï­³ú¹øï¯ï°Ðâï±ï²·æÐ¿ï³ï´ïµÈñÌàï¶ï·ï¸ï¹ïºÕà´íÃªï¼èŸï¾"
			+ "ï¿è ÎýïÀÂà´¸´¸×¶½õé@ÏÇïÃïÂïÄ¶§¼ü¾âÃÌïÅïÆéAïÇïÏïÈïÉïÊÇÂïñ¶ÍïËéBïÌïÍ¶ÆÃ¾ïÎéCïÑïÒÕòéDïÓÄ÷éEïÔÄøïÕïÖ¸ä°÷"
			+ "ï×ïØïÙéFïÚïÛïÜïÝéGïÞ¾µïáïßïàéHïâïãÁÍïäïåïæïçïèïéïêïëïìÀØéIïíÁ­ïîïïïðéJéKÏâ³¤ÃÅãÅÉÁãÆê\±ÕÎÊ´³ÈòãÇÏÐÏÐ"
			+ "ãÈ¼äãÉãÊÃÆÕ¢ÄÖÄÖ¹ëÎÅãËÃöãÌê]·§¸óºÒãÍãÎÔÄãÏê^ãÐÑËãÑãÒãÓãÔÑÖãÕ²ûÀ»ãÖê_À«ã×ãØãÙê`ãÚãÛêa¶ÓÑôÒõÕó½×¼ÊÂ½Â¤"
			+ "³ÂÚêÉÂÚíÔÉÏÕËæÒþÁ¥öÁÄÑ¹Í³ûµñöÅö¨Îíö«Ã¹ö°ö¦¾²ÃæØÌ÷²÷³÷µ÷¹Î¤ÈÍí‚º«è¸è¹èºÔÏÒ³¶¥ÇêñüÏîË³ÐëÐëçïÍç¹Ë¶Ùñý°ä"
			+ "ËÌñþÔ¤Â­ÁìÆÄ¾±ò¡¼ÕïFò¢ò£ïGò¤ÒÃÆµïHÍÇÍÇò¥ïIÓ±¿ÅÌâïJò¦ò§ÑÕ¶îò¨ò©µßòªò«ïK²üò¬ò­È§·çïrïsì©ìªì«ïtì¬ïuïvÆ®"
			+ "ì­ì®·É÷Ï÷Ðð—¼¢¼¢ð˜â¼â½â¾â¿âÀâÁ·¹Òû½¤ÊÎ±¥ËÇð™âÂ¶üÈÄâÃðšð›½Èðœ±ýâÄð¶öâÅÄÙðžðŸâÆÏÚ¹ÝâÇÀ¡À¡ð âÈ²öñ@âÉñA"
			+ "ÁóâÊâËÂøâÌâÍâÎÂíÔ¦ÍÔÑ±³ÛÇýÇýóR²µ²µÂ¿æàÊ»æáæâ¾Ôæã×¤ÍÕæå¼ÝæäæææçÂîóS½¾æèÂæº§æéóTæê³ÒÑéóUóV¿¥æëÆïæìæíóW"
			+ "óXæîÆ­æïóYÉ§æðæñæòå¹æóæôÂâæõæöÖèæ÷óZæø÷Ã÷Å÷Æ××÷Þ÷Ê÷ËÓã÷÷‚öÏ÷ƒÂ³öÐ÷…öÑöÒöÓöÔ÷†÷‡öÖ÷ˆ±«ö×÷‰öØöÙöÚ÷ŠöÛ"
			+ "öÜ÷‹÷Œ÷÷ŽöÝöÞÏÊ÷ößöàöáöáöâöãöäÀðöåöæöçöèöé÷öê÷‘öëöì÷’öíöîöïöðöñöòöóöô¾¨÷“öõööö÷öø÷”÷•÷–÷—÷˜Èúöùöú"
			+ "öûöü÷™÷šöýöþ÷¡÷¡÷¢÷£÷¤÷¥÷›÷œ÷¦÷§÷¨±î÷©÷ª÷«÷ž÷¬÷­ÁÛ÷®÷Ÿ÷ ÷¯ø@Äñð¯¼¦ð°Ãùû\Å¸Ñ»û]ð±ð²ð³ð´ðµÑ¼û^Ñìû_ð·ð¶"
			+ "Ô§û`ÍÒð¸ðºð¹ð»ð¼ûaûb¸ëð½ºèûcð¾ð¿¾éðÀ¶ìðÁðÂðÃðÄÈµðÅðÆûdðÇÅôûeðÈûfûgûhðÉûiðÊ÷½ûjðËðËðÌðÍûkðÎûlûmûnûoðÏ"
			+ "º×ûpðÐðÑðÒðÓðÔðÕðÖðØûrÓ¥ð×ûsðÙûtõºÂóôï»ÆÙäüd÷ò÷õö¼ö½ü…ö¾Ø»÷ú÷þÆëì´³Ýö³ý†ý‡ö´Áäöµö¶ö·ö¸ö¹öºÈ£ö»Áú¹¨íè¹ê";

	public static String TRADITIONAL_CHARS = "ÈfÅcáhŒ£˜I…²–|½zGƒÉ‡À†Ê‚€¹wãÝØSÅRžéûÅeÞ•üNÁxžõ˜·†ÌÁ•àl•øÙIy Žì¶Ìë…ƒ†®a®€ÓHÒC‡¾ƒ|ƒHƒWÄö‘‚}"
			+ "ƒx‚ƒƒr±Šƒžâ·•þ‚ø‚ã¿™‚¥‚÷‚û‚t‚‚á‚ÎÐµvówðN·‚òƒL«˜Š©‚b‚Hƒe‚É‚ÈƒSƒ~ƒŠƒz‚Rƒ‰ƒ°‚zƒ«ƒ€Ã‘î\‚ùƒA‚ô‹ƒEƒfƒ”"
			+ "ƒ¯ƒ†ƒ¦ƒ®ƒº„wƒ¶ƒ¼ühÌmêPÅdÆðB«F‡ÏƒÈŒùƒÔŒ‘ÜŠÞr‰VüŠñT›ZÐn›_›Q›rƒöœQœDœÊ›öœRœpœ„CŽ×øPøD‘{„Pýi“ôšëèÆc„"
			+ "„¢„t„‚„“„hãE„e•„}„qïWÑu„£„¥„’„©„Ž„¦„ƒ„¡Ù‹„à„ñÞk„Õ„ê„Ó„î„Å„Ú„Ý„ìÃÍ„Ô„ã„ò…Q…T…^átía•Nê…ÈA…f†ÎÙuÊN×±R"
			+ "FûuÅPÐl…s’ÔŽ„Sdšv•Ñ…–‰º…’…‡ŽúáŽû…˜BNŽýP¿h…¢ìaì^ëp°lóŒ×ƒ”¢¯BëbïU™…Å_È~Ì–šU‡@‡\Ó’»n†ËàAéxtáá•Ú"
			+ "‡»‡˜…Î†á†w‡Â †¢…Çªy‡`‡Ò‡I‡³†h†T†J†Ü†èßLàÀßüÔ†U‡µ‡“‡zß¸ÚÑ‚Ì‡jûy‡²ô\ßÉí‘†¡‡}‡^†ô‡‚‡WÖœ‡ˆ‡‡†ÑÃ‹‡O†ß"
			+ "‡Z†¤†î†r†¾ºô‡K†Ý‡Êým‡Ó‡c‡[ðj‡Š‡D‡¿‡Ë‡†‡u‡Â‡Ú×@‡£×YÅü‡ÌÖoÞ’¼aˆFˆ@±—‡è‡ú‡÷‡øˆDˆAÂ}‰¿ˆöênÚæ‰Ä‰KˆÔÀ—‰¯‰È"
			+ "‰Î‰]‰ž‰‹‰Å‰ÅÀž‰À‰¾‰¨ˆsˆ×‰|ˆº‰¡‰³‰Nˆß‰P‰¶‰_ˆå‰|ˆ‰q‰™ê‰Ïà{ ‰ÑÂ•š¤‰Ø‰ÚÌŽ‚äÍÑ}‰ò¸î^ÕFŠAŠZŠYŠJŠ^ª„ŠW"
			+ "‹èŠ¦Šy‹D‹Œ‹³‹ž‹‚Š™ËKŠä‹I‹Æ‹ÉŒD‹úŠÊ‹z‹¹‹½‹ë‹È‹ð‹‹‹Ü‹å‹Ô‹ßŒOŒWŒ\å¸ŒŽŒšŒŒ™Œ‘—Œm‚íŒ’ÙeŒ‹Œ¦Œ¤Œ§‰ÛŒ¢ –‰m"
			+ "‡Ÿ‡LˆòŒÀŒÆ±MƒÛR‚IŒÓŒÚŒÏŒÃŒÙŒÒŒÕŽZšqØMçsŽS¹u†ÇŽrŽXŽ[–ŽhŽGŽF{ŽAþ˜Žn÷ˆŽMäŽVô£â¼¹Žpâ ì–"
			+ "Ž€ŽÅÑŽ›ŽŸŽ®Ž¤ºŸŽÃŽ§Ž¬ÉtŽÍŽÎŽ¾Ž½ƒçÒLÇ¬ŽÖ˜oK‚†VÇf‘c —]TŽì‘ªRý‹UÈCF[é_®—‰ˆ›ž…½L†—™Ššw"
			+ "®”ä›§©¾ZÏØáç½ÞŸÆ¶R‘›‘ÔÕI‘n†ˆ÷‘Ñ‘B‘Z‘“‘Yí‘z¿‚‘»‘«‘Ùa…r‘©º‡f‘Q‘ÃðÅÀÁ‚â‘Ò‘a‘‘ó@‘Ö‘K‘Í"
			+ "‘vÜ‘M‘„‘T°Kœ¡‘C‘‘|îŠ‘Ø‘b‘€âð‘¿‘Ð‘¬‘ß‘â‘ò‘ê‘ð‘h‘ì‘ô¼™“äÓšâ@’LˆÌ”U’Ð’ß“Pï^”_ß¡“á’“»“¸’à“Œ×oˆó ¹Óh“ú"
			+ "–¡”M”n’þ“í”r”Q“Ü“ñÞÕ’ì“´”’é“ë“é’¶“Ï”†“õ“×’ê”D“]“ÍÝn½™—y“Æ“p“ì“Q“v“þ“Ó“ï“”S“Û“½“¥“«”ˆ“å”v”R“§”‡ÉL”y"
			+ "”z”d”[“u”P”‚”t“Î”f”X”]”x”\”€”³”¿”µýS”ÌôY”Ø”àæ›”çŸoÅf•r•ç•ª‹•Ò•¿•ƒ•îï@•x•ñ•Ô•Ï•ž•Ÿ•ºŸœ•áôð„žÐg³p˜ã™C"
			+ "š¢ës™à—U—lí—î˜q° ‚Üó é›˜O˜‹“k˜º˜Ð——™À—g—–˜Œ—÷—n™™™Ž™f—d–Å˜Ë—£™±™É—™¾™µ™Ú˜ä—«ü˜ÓÒ‡™è—¨—¿˜ï˜E™n˜˜ò"
			+ "˜å™u˜ª˜¶‰ô™„—®™z™Ð™ô¹Š¶ ™˜˜¡™³˜ ™å™E˜Ç™ì™Â™°™Î“’™x™‘™‰™½¼U™M™{™Ñ™Á™»™©™´º™™_šg‘×ÔDšešW‘jšžš{š‘šˆšŒšš"
			+ "š—š›šª‘@ Sš§Ýž®…”ÀšÖšÐšÚšâšäšåšè¡…Rh›@œ«›°ßeœÏ›]ƒÓž–ažrœSœæœtœ¿œûðë•›ªšïšøôÔ]œIÍž{žožTžaŠÉ›Ü"
			+ "ž¢¸D›Ñœ\{²œœÛáœyÒúžgIœ†Gâ¡øžF‰Tœ¥ý³œZi¬œuœÝœoœì™¾q­ÕœYœOnž^uÆOžcBœØß[ž³ñ"
			+ "¢žRsUìCØG§Lœþž·ž¹ž—Mž]žVžEž´žIž©Ëž¦žEžužtž‡žH“žz¯ž‘ž|žlž®œçŸôì`žÄ NŸ¬ tèzŸõŸ˜ŸÍ³hücå€Ÿ’Ÿë q"
			+ " €ŸN TÝÎŸŸŸ©ŸýŸî Z C aŸáäIâFŸ¨ F cŸžÝºýÞ¹Áï×…á€Û ” © ÓšÓ ¿ Þ ÙŠ î«EªwªqªNûƒªžªŸªšªMª{ªœªbªzªsØ‚ª"
			+ "«C«J«MØiØˆÎo«I«H­^­m¬„¬”¬|­h¬F¬š­t¬z«k¬m­‡­c¬qšÂ¬˜­\­I¬­‚¬Ž­a­v­‹­‘Àš®Y®TëŠ®‹•³ÙÜ® °X¯Ÿ¯‘°O¯ƒóœ¯¯‚"
			+ "°’åí°Y°b¯d°W¯{°A¯ˆ°B°V¯w°D¯”¯Ž¯›°T°c°a°`°]°_°dÄŸ°}°™°—±Kû}±OÉw±I±P˜„ÅÍ²g±{²”Ò›Öø± ²A²€²m²š³C´‰µ\µV´X"
			+ "´a´u‰t³Œ³Ž´^µZµaµ[µA³Îù´T³ˆ‰Œ´“´o´™´_û|µK´ƒ´~‰Aû|ïàLŸû¶Y¶B¶[µ¶\µœ·Aµ“¶Uëx¶d¶’íF·Nµz·e·Q·x·v·„¶·d"
			+ "·€·w¸F¸`¸[¸G¸Z¸C¸QêN¸]¸MØQ¸‚ÂZºV¹S¹P¹a¹{  »\»eºBº`ºYºš¹~ºS»Iºž»`º†»U¬gºjºD»X»jº„ººˆºt»@»h»f»[¼eî¶i"
			+ "¼g¼c»›¼S¼Z¼Rðf‚SÀM¾oÀn¿{ôé¼m¼u¼t¼qÀw¼v¼s¼‰¼wÀk¼o¼x¾•¼‹¼‡¼ƒ¼„¼†¾V¼{¼Œ¿v¾]¼Š¼ˆ¼y¼¼Ÿ¼…¼~¼‚¾€½C½X¼œ¼›¾š"
			+ "½M¼¼š¿—½K¿U½O½E½I½BÀ[½›½H½‰Ás½q½Y½fÀ@½x½WÀL½o½k½{½j½^½g½y½Ž½‹½ÀC½”½—¿_½dÀ^½¿ƒ¾w¾c¾xÀm¾_¾p¾b¾y¾iÀK"
			+ "¾S¾d¾R¿‡¾I¾T¾^¾J¾C¾`¾U¾G¾Y¾l¾~¾|¾}¾’À|¾Ÿ¾˜¾ƒ¿Z¿AÀD¾Œ¾E¾„¾œ¾€¾—¿P¾¾†¿|¾Ž¾‡¾‰¿N¿`¿d¿b¿p¿\¿cÀp¿r¿O¿VÀ_"
			+ "¿~¿z¿wÀt¿s¿Š¿‰Ài¿¿˜¿•í\À`ÀRÀQÀUÀyÀ›®Z¾WÁ_ÁPÁTÁ`ÁbÁuÁwÂNÂPÂE¿œ”ŽÂgÂeÂ–uÂ™Ã@ÂšÂœÂ“Â˜Â”ÃCÄcÄwðPÄdÄIÄ["
			+ "Ã›Ã{Ä‘“d„ÙôEÐk–VÄLÅFÃ„ÄzÃ}Ä’óvÅKÄšÄXÄ“ÅLÄ_Ã“ÄTÄ˜ÅDáZÄsý|ÄìtÄeòvÄœÅN¿@•Ý›’ÎÅœÅžÅ“ÆAÆDØWÆHË‡¹ÁdËGÊ"
			+ "ÌJÊ|ÉÈ”ËžÇ{ÈOÉnÆrÌK®d™”ÌO¹ ÇoÌdÊ\‰LŸ¦ÀOÇGË]ËRÇvÊÉœÊwËCËjÊŽ±U˜sÈœî ÎŸÉÊnË|ÉpÊaÊ{È‡È’ËŽÈ™ÉWÉ‰ÈRÉÉP"
			+ "ÈnËW«@·‚Ê~¬“úLÉ”ÌEÌ}Îž I¿MÊ’Ë_Ê[ÊrÊ‰ÊYÊV²‰÷ºwË{ËEÌyÊšævò‡Ð`ËNÌ`ÌAÌ@ÌIÌNË’éÂÌ\»LÌ”‘]Ì“ÏxÍAÏlÎtëmÎrÏŠ"
			+ "ÎgÏÎ•Î›ÐQÏ–Í˜ÐMÏ Ï|ÐUÏUÍÏuÎ‡Ï“Í‘ÎÏžÛmÏ‰ÏXÏsÏÏNÏ”ÏQÏ\ÐDá…ã•†¥ÑaålÒrÐ–Ò\‹–‹ØÑ‹ÒmÒuÒUÑbÒdÑ‚ÑžÒcÑÒM"
			+ "Ò@Òh¿‹ÒwÒŠÓ^ÒÒŽÒ’Ò•Ò—Ó[ÓXÓJÒ Ó]ÓCÓDÓMÓPÓUÓxÓ|Óz×„×uÖ`Ó…Ó‹Ó†Ó‡ÕJ×IÓ“ÓÓ‘×ŒÓ˜Ó™Ó–×hÓÓ›Ó•ÖvÖMÖŽÔnÓ ÔG"
			+ "ÔSÓžÕ“ÔKÔAÖSÔOÔLÔE×CÔ^ÔbÔXÔuÔ{×RÔwÔpÔVÔ\ÔgÖaÔ~ÔxÔtÔv×gÔrÕEÕCÔ‡ÔŸÔŠÔ‘ÔœÕ\ÕDÔ–Ô’ÕQÔÔÔŽÔƒÔ„ÕŠÔ“Ô”ÔŒÕŸ"
			+ "Ô‚×pÕ]Õ_ÕZÕVÕ`ÕaÕTÕdÕNÕfÕbÕOÕˆÖTÕŒÖZ×xÕŽÕuÕnÕ†Õ˜ÕlÕ”Õ{Õ~ÕÕÕrÕ„ÕxÖ\ÖRÕ™ÖeÖGÖCÖoÖ]Ö^Ö@ÖIÖX×‹ÖJÖOÖVÖB"
			+ "ÖiÕ›ÕšÖƒ×•ÖqÖxÖ{ÖrÕžÖtÖkÖ”Ö™Ö†×vÖ‡×T×P×S×Ž×V×H×—×l×d×·YØrØØ‘Ø“Ø’Ø•Ø”ØŸÙt”¡Ù~Ø›Ù|ØœØØšÙHÙÙAØžÙEÙv"
			+ "ÙSÙBÙNÙFÙLÙJÙQÙMÙRÙOÙ\Ù—ÙZÙVÙDÙUÙTÚEÙYÙWÚBÙgÙcÙlÙdÙxÙ€ýVÚHÙpÙnÚFÙkÙsÙrÙyÙ‡ÙˆÙ˜ÙŽÙÙÙ‘ÚIÙ×“ÙšÙ›Ù ÚA"
			+ "ÚMÚXÚwÚsÚ…ÚŽÜOÜSÛ„Û•ÜVÛ`ÜJÜEÛ‹Ü]ÜQÛxÜPÛ™ÜWÜUÜbÛ˜ÜXÜfÜkÜgÜ|Ü‡ÜˆÜ‰ÜŽÜÜÞDÜ—Ý†Ü›ÞZÝMÝVÞ_ÝSÝTÝWÜ ÝFÞ]ÝU"
			+ "ÝpÝYÝdÝeÞIÝcÝbÝ`Ý^ÝmÝoÝvÝ‚Ý…ŸÝxÝÝyÝˆÝzÝwÝÝ—Ý‹ÝœÝ”Þ\Þ@Ý ÝšÞAÞHÞOÞoêVÞqÞpß…ß|ß_ßwß^ß~ß\ß€ß@ßMßhß`ßB"
			+ "ßtßƒ¯BÞŸÛEÛ”ßmßxßdßfßŠß‰ßzßbà‡à—àwà]àuà’àôdàSàPà”ààiáBàyàájáwõµáu¯iá‰á‡á„’ñáŒÑeÑYîÒèaèbèŽçYááæP"
			+ "á˜á”á“á•á‘âQâTâAáŸâlâCážå{âSåâOâ]â}âbââgânæRçŠâcä^ä“âkâjè€»ašJâxæuã^â‚â[â€â^âoâZâ•åXã`ãQâ’ÀâŽãOâ˜"
			+ "â“ãXèãfãgâ›âšâ™èFãKâèpãUãTâ‹ãCãBãGâ‰â”èIãoäDã™ãsä€åEäBäeäyçtã‡èKã~äXäHãŸæzåŽããŠäbäAã”çfãŒæ|ãxã“ãt"
			+ "ã‘åPäCãqãž„•çPã|ç|ä@ãyãœèTç„äÅmäoånäˆæœçHäNæiä‡ä{äzåä†ä~çnäSäsähä\äç˜ç™äJäRäZäuä|åHäæNåeå^åQåWä˜"
			+ "åKå_åaådèŒåNæmåFå\èeåväŸäžåUåVæIäåiåOå›å}å|çIæJåŠåšæ@æRå‘æ}å–æDæXåƒæVçUætäYæŸæ‚ænækè‡è’çæ‡æ“æyæ€æ^"
			+ "æ„æ‰è\ægçSçMçNæ çaçOçRçCæ—æ›çBç†è‘ç‚çhèuç…è|ç’è‰çjç‹èZèDèGèCç èOèdèsènèè‚éLéTéVéWéZé\é]†–êJécééfée"
			+ "ébégéhé`žélô\ô[é|Â„êYé}é‚êGéyéwéué€ôbé†éêAé“éŽé‹ô]é”é’éé‘êUê@é˜êTéŸé êHêDêFêIêRêXê ê–êŽê‡ëAëHê‘ë]"
			+ "êê€ê„êŸëEëUëSë[ë`ëhëyƒlër¬×‡ìZìFìVüqì\ìnìoüIìví^íXídíxífígíhínítíyíwíí“í”í•í™í—í˜íšôPíœîBî™îDí îC"
			+ "ížî@îAïBîIîHîiîRîaîcîM}ŸâîWîUîlî_îjînîhîe·fîwî}î„î€î…îî~ïDî”îî‹î—ÀhîîžïAïEïLï^ïQïRïSïZï\ï`ï_ïdïh"
			+ "ïjïjïwð‹ðï}ï|ð‡ï€ðhï‚ðqïƒï„ï†ïˆï‹ðTï—ï–ï•ï˜ïðDðˆðAïðEïœðFïžðGðLðIðNðHðKðRðQðWð^ðlððrðkðtð’ðvðxðo"
			+ "ðsð}ð~ðzð€ð‚ð–ñRñSñWñZñY”·òŒñ_ñgñ•óHñzñ‚ñ†ñ€ñxò|ñvñ„ñwñ{óAñ~ò”ÁRñ—òœò‘ñ˜ñ”ñ‰óQóPòGòžòHñŸòEòUòTòSòKòR"
			+ "ò“ò‰ò_òsòjò}ò\òˆòtòqò~òŠò…ò‹ò–óEóKóLóJótóyóxòiôWô|ôuô~ô€ô‡ôœôô”ô™ôŸõEõGöT÷|õOõWõVõNõU÷cõQõTõqõ^õwõn"
			+ "õbõjöfõ`÷d÷qõoõrõ~õœ÷\õ†ói÷~ö–öžõŽöˆöœõ…õõŒõzöaõ—õ›öNõšöOöEöHöKöAöFöTõ öLöYöXõ™÷aölös÷lö[ö“ögöw÷{öq"
			+ "övömöe÷Föcö…üö—ö’ööŠöŽö„öö˜÷B÷L÷Mö öš÷I÷@÷Z÷X÷[÷V÷s÷h÷k÷gøBøFëuøSøQøOútøfúIødøcøù…ûRø†ø{ø„øoø|øz"
			+ "øxøŠørúƒúvøøŽø ø’ù@øû[ø™ùMùPûZùNù]ùZùOú‘ùYù^ùoù‘ùgúAùlùiùkù‡ùˆùtú‰ù–ùŸù˜úXù”ú\ú]úBúFúgú_úOúVúWú^úY"
			+ "úQúsûWúpúwúú„úú–ú˜ûDú—ûIûLûXûUûzûœûŸüSüZüsütüoüwüxü{üƒìŠýBýOýRýWýXýZý[ý]ýeýgý_ýfýbýlýrýpýxý}ýˆýýý”";
}