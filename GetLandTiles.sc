Program New;
var
i:integer;
    X0,Y0,Z0,Tile: Word;
    Tile0: TStaticCell;
    C,W0: Byte;
begin
 // repeat
    i:=0;
      X0:=1455;
      Y0:=1528;
      W0:=WorldNum;
      Tile0:=ReadStaticsXY(X0,Y0,W0);
      C:=GetLayerCount(X0,Y0,WorldNum);
      Addtosystemjournal('GetLayerCount = '+IntToStr(c)+' | X = ' +IntToStr(X0)+' | Y = '+IntToStr(Y0));            
      Addtosystemjournal('Tile = ' +IntToStr(GetMapCell(X0,Y0,WorldNum).Tile)+' | Z = ' +IntToStr(GetMapCell(X0,Y0,WorldNum).Z));
      Tile:= (GetMapCell(X0,Y0,WorldNum).Tile); 
       Addtosystemjournal('Tile = ' +IntToStr(GetMapCell(X0,Y0,WorldNum).Tile)+' | Z = ' +IntToStr(GetMapCell(X0,Y0,WorldNum).Z));
      Tile:= (GetMapCell(X0,Y0,WorldNum).Tile);
    {  Addtosystemjournal('Layers = '+IntToStr(Tile0.StaticCount)+' | Tile = ' +IntToStr(Tile0.Statics[0].Tile)+
      ' | X = ' +IntToStr(Tile0.Statics[0].X)+' | Y = ' +IntToStr(Tile0.Statics[0].Y)+' | Z = ' +IntToStr(Tile0.Statics[0].Z)+' | Color = ' +IntToStr(Tile0.Statics[0].Color));
      Addtosystemjournal('Tile = ' +IntToStr(GetMapCell(X0,Y0,WorldNum).Tile)+' | Z = ' +IntToStr(GetMapCell(X0,Y0,WorldNum).Z));
      Tile:= (GetMapCell(X0,Y0,WorldNum).Tile);
      Addtosystemjournal('Flags = '+IntToHex(GetLandTileData(Tile).Flags,8)+' | TextureID = ' +IntToStr(GetLandTileData(Tile).TextureID));
      Addtosystemjournal('Flags = '+IntToHex(GetStaticTileData(Tile).Flags,8)+' | Weight = ' +IntToStr(GetStaticTileData(Tile).Weight)+' | Height = ' +IntToStr(GetStaticTileData(Tile).Height));    } 

//  until (1<>1);
end.