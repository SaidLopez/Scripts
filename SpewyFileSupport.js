var ForReading = 1, ForWriting = 2, ForAppending = 8 

var WSHShell = new ActiveXObject("WScript.Shell"); 
if (WSHShell)
{
	var args = WScript.Arguments; 
	var objArgs = WScript.Arguments; 
	if (objArgs.length > 3) { 
	    var filename = objArgs(0); 
	    var create = parseInt(objArgs(1)); 
	    var startVar = parseInt(objArgs(2)); 
	    var endVar = parseInt(objArgs(3)); 

	    var fso = new ActiveXObject("Scripting.FileSystemObject"); 
	    if (fso) 
	    { 
		var f 
		if (create = 1) 
		    f = fso.CreateTextFile(filename, true); 
		else 
		    f = fso.OpenTextFile(filename, ForAppending, true) 
		if (f) 
		{ 
		    for (var index = startVar; index <= endVar; index++) 
		    { 
			f.Write(WSHShell.RegRead("HKCU\\Software\\EasyUO\\*"+ index)); 
			f.Write("\r\n"); 
		    } 
		    f.Close(); 
		    WSHShell.RegWrite("HKCU\\Software\\EasyUO\\*1", 1); 
		} else  { 
		    WSHShell.RegWrite("HKCU\\Software\\EasyUO\\*1", 2); 
		} 
	    } else { 
		WSHShell.RegWrite("HKCU\\Software\\EasyUO\\*1", 3); 
	    } 
	} else {
	    WSHShell.RegWrite("HKCU\\Software\\EasyUO\\*1", 4); 
	}
}
