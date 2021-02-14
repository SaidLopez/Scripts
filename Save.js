var ForReading = 1, ForWriting = 2, ForAppending = 8

var args = WScript.Arguments
var objArgs = WScript.Arguments;
if (objArgs.length > 1) {
	var filename = objArgs(0);
	var variable = parseInt(objArgs(1));

	var fso = new ActiveXObject("Scripting.FileSystemObject");
	if (fso)
	{
		var f = fso.OpenTextFile(filename, ForAppending, true)
		if (f)
		{
			var WSHShell = new ActiveXObject("WScript.Shell")
			if (WSHShell)
			{
				f.Write(WSHShell.RegRead("HKCU\\Software\\EasyUO\\*"+variable));
				f.Write("\r\n");
			}
			f.Close();
		}
	}
}