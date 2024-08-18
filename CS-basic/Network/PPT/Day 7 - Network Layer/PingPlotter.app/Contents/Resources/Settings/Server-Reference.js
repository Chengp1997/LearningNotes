//  root  the root meta object
//  tree  the MetaUITree
//  meta  currently scoped MetaControl (usually the root of the current navigation item)
var PacketTypeCB_OnChangedValue = function (newPacketType) {

   ViewModel.SetPingType(newPacketType.Value, true);

   var packetSize = root['ServerSettings.Configurations.ServerConfiguration.Engine.PacketSize'];

   // This is to account for packet types not knowing what their defaults are. ToDo: Fix me.
   if (newPacketType.Value == "WindowsPCapTcp") {
      //log('About to set the value to 40.')
      packetSize.Setting.SetValue(40);
   }
   else {
      //log('About to set the value to 56.')
      packetSize.Setting.SetValue(56);
   }

   packetSize.Sync();

   var navParent = packetSize.GetNavigationParent();
   if (navParent != null) {
      navParent.GenerateBranch();
   }

}


