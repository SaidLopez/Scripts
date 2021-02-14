Program Residue;
var
i:integer;

const
tong = $0FBB;
dagger = $0F52;
recycle = $4006C778;
iron = $1BF2;

begin
 for i:=0 to 40 do
 begin
 findtype(tong,backpack);
 useobject(finditem);
 wait(300);
 NumGumpButton(GetGumpsCount-1,30);
 wait(2000);
 findtype(dagger,backpack);
 moveitem(finditem,1,recycle,0,0,0); 
 end;
 useskill('Imbuing');
 wait(200);
 NumGumpButton(GetGumpsCount-1,10011);
 waitfortarget(1000);    
 targettoobject(recycle);
 waitfortarget(200);
 NumGumpButton(GetGumpsCount-1,1); 
end.