# A0134109Nreused
###### collated\main\jarvas\JParser.java
``` java
	public static Date dateConverter(String inputDate){
		List<DateGroup> groups = parser.parse(inputDate);
		Date convertedDate = null;
		
			for(DateGroup group:groups)  {
			    Date dates = group.getDates().get(0);    
			    int line = group.getLine();
			    int column = group.getPosition();
			    String matchingValue = group.getText();
			    String syntaxTree = group.getSyntaxTree().toStringTree();
			    Map parseMap = group.getParseLocations();
			    boolean isRecurreing = group.isRecurring();
			    Date recursUntil = group.getRecursUntil();
			    convertedDate = dates;
			    
			   }

		return convertedDate;
	}
	
```
