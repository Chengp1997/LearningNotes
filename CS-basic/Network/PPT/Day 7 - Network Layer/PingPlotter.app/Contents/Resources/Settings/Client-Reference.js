function escapeHTML(s) {
   if (!s) return "";
   return String(s).replace(/&/g, '&amp;')
           .replace(/"/g, '&quot;')
           .replace(/'/g, '&#39;')
           .replace(/</g, '&lt;')
           .replace(/>/g, '&gt;');
}

String.prototype.format = function () {
   var formatted = this;
   for (var i = 0; i < arguments.length; i++) {
      var regexp = new RegExp('\\{' + i + '\\}', 'gi');
      formatted = formatted.replace(regexp, arguments[i]);
   }
   return formatted;
};

function renderLabel(parent, traitName) {
   // Find the trait.
   var trait;
   trait = Traits[traitName];

   if (!trait) { throw new Error(traitName + " trait/editor configuration not found"); }
   if (!parent) { throw new Error(traitName + " no parent found"); }

   AppendHtml(parent, escapeHTML(trait.Label));
}

var timespanParser = importNamespace('Xerek.Utils').TimeSpanParser;
var timeExamineInputControl = $control("#timeExamineInput");

const VALUETYPE_TIMESPAN = 13;

function renderInput(parent, traitName, options) {
   try {
      var setting;
      var formatString;
      var className;
      if (options) {
         setting = options.Setting;
         formatString = options.FormatString;
         className = options.ClassName;
      }

      if (!traitName) { throw new Error("No trait name specified to renderInput"); }
      // Find the trait.
      var trait;
      trait = Traits[traitName];

      if (!trait) { throw new Error(traitName + " trait/editor configuration not found"); }

      if ((!setting) && (!options || !('Setting' in options))) {
         setting = Settings[traitName];
      }

      if (!parent) { throw new Error(traitName + " no parent found"); }

      // Get the control type, normalized
      var controlType = "";
      if (trait && trait.Type) {
         controlType = trait.Type.toLowerCase();
      }
      if (!formatString) {
         formatString = "{0}";
      }
      if (!className) {
         className = "";
      }
      style = "";
      if ((trait.Attributes) && (trait.Attributes.MaxChars)) {
         style += "width:" + (trait.Attributes.MaxChars * 10) + "px;";
      }
      if (options && options.Style) {
         style += options.Style;
      }
      var id = traitName;
      if (setting) {
         var id = setting.Name;
      }
      if ((trait.Attributes) && (trait.Attributes.ID)) {
         id = trait.Attributes.ID;
      }

      // Create the appropriate element.
      var newEditor;
      if ((controlType == "textbox") || (controlType == "password")) {
         htmlControlType = (controlType == "textbox") ? "text" : controlType;
         newEditor = AppendEditorHtml(parent, formatString.format("<input id='" + id + "' type='" + htmlControlType + "' style='" + style + "' title='" + escapeHTML(trait.Hint) + "' class='" + className + "'>"));
         if (setting) {
            newEditor.Text = setting.ValueStr;
            newEditor.SubscribeChange(function (c) {
               setting.SetValueFromString(newEditor.Text);
            });
         }
      } else if (controlType == "textarea") {
         newEditor = AppendEditorHtml(parent, formatString.format("<textarea id='" + id + "' style='" + style + "' title='" + escapeHTML(trait.Hint) + "' class='" + className + "'></textarea>"));
         if (setting) {
            newEditor.Text = setting.ValueStr;
            newEditor.SubscribeChange(function (c) {
               setting.SetValueFromString(newEditor.Text);
            });
         }
      } else if (controlType == "checkbox") {
         var checkedValue = "";
         if ((setting) && (setting.Value)) { checkedValue = "checked "; }
         newEditor = AppendEditorHtml(parent, formatString.format("<input id='" + id + "' " + checkedValue + "title='" + escapeHTML(trait.Hint) + "' label='" + escapeHTML(trait.Label) + "' type='checkbox' class='" + className + "'>"));

         if (setting) {
            newEditor.SubscribeChange(function (c) {
               setting.SetValue(newEditor.IsChecked);
            });
         }
      } else if ((controlType == "combobox") || (controlType == "comboboxedit")) {
         options = "";
         var selectedIndex = -1;
         var index = 0;
         trait.Attributes.Items.ForEach(function (option) {
            if (!option.Text) {
               option = { Value: option, Text: option };
            }
            if (setting) {
               if (option.Value == setting.Value) { selectedIndex = index; }
            }
            index++;
            options += "<option value='" + escapeHTML(option.Value) + "'>" + escapeHTML(option.Text) + "</option>";
         });
         newEditor = AppendEditorHtml(parent, formatString.format("<select " + (controlType == "comboboxedit" ? "editable " : "") + "id='" + id + "' title='" + escapeHTML(trait.Hint) + "' class='" + className + "' style='" + style + "'>" + options + "</select>"));
         if (setting) {
            newEditor.SelectedOptionIndex = selectedIndex;
            newEditor.SubscribeChange(function (c) {
               setting.Value = trait.Attributes.Items[newEditor.SelectedOptionIndex].Value;
            });
         }
      } else {
         newEditor = AppendEditorHtml(parent, formatString.format("<div id='" + id + "'>" + controlType + ":" + traitName + "</div>"));
      }
      return newEditor;

   } catch (err) {
      throw new Error("Error building input for " + traitName + ": " + err.message);
   }

}

function renderRadioGroup(parent, traitName, options) {
   try {
      var trait = Traits[traitName];
      if (!trait) throw new Error("RadioGroup " + traitName + " trait not found.");
      if (!parent) throw new Error("Parent for radiogroup " + traitName + " not assigned");
      var setting = null;
      var className = "";
      var formatString = "<div class='editLine'>{0}</div>\n";
      if (options) {
         setting = options.Setting;
         formatString = options.FormatString;
         className = options.ClassName;
      }
      // If an empty Setting was not passed in, try to get a setting from the trait name.
      if ((!setting) && (!options || !('Setting' in options))) {
         setting = Settings[traitName];
      }
      var groupName = trait.Name;
      if (!groupName) { groupName = "Temp_Group"; }
      var groupHtml = "<div class='editLine'>" + trait.Label + "</div>";
      AppendHtml(parent, groupHtml);
      var rbArray = [];
      var editor = null;
      trait.Attributes.Items.forEach(function (radioItem) {
         var style = "";
         var editorSpan = "";
         var IDAttr = "";
         if (radioItem.ID) {
            IDAttr = "id='" + radioItem.ID + "'";
         }
         if (radioItem.EditorTrait) {
            // We're going to put an editor in to the right of the radio - for now, this means the radio needs to specify width
            // and we'll force the input width.
            if (!radioItem.Text) { throw new Error("Trait: " + traitName + " has no radio button text."); }
            style = "width:" + (1.5 + (radioItem.Text.length / 2)) + "em";
            editorSpan = "<span id='" + groupName + "_editor'></span>";
         }

         radioHtml = "<input " + IDAttr + " style='" + style + "' name='" + groupName + "' type='radio' " +
                     "title='" + escapeHTML(radioItem.Hint) + "' label='" + escapeHTML(radioItem.Text) +
                  "' class='" + className + "'/>" + editorSpan;
         var rbData = {};
         rbData.RadioButton = AppendEditorHtml(parent, formatString.format(radioHtml));
         rbData.Value = radioItem.Value;
         rbData.Default = radioItem.Default;
         if (radioItem.EditorTrait) {
            editor = renderInput($("#" + groupName + "_editor"), radioItem.EditorTrait, { Setting: null });
            if (setting) {
               if (setting.Value > 0) {
                  editor.Text = setting.ValueStr;
               } else {
                  editor.Text = radioItem.Default;
               }
               editor.SubscribeChange(function (c) {
                  setting.SetValueFromString(editor.Text);
               });
            }
            rbData.editor = editor;
         }
         rbArray.push(rbData);
      }
      );
      if (setting) {
         var isChecked = false;
         rbArray.forEach(function (radioItem) {
            bindRadioItem(radioItem, rbArray, setting, editor);
            if (!isChecked) {
               if ((radioItem.Value == setting.Value) || (radioItem.editor)) {
                  radioItem.RadioButton.IsChecked = true;
                  isChecked = true;
               }
            }
         });
      }
   } catch (err) {
      throw new Error("Error building groupbox for " + traitName + ": " + err.message);
   }

}

function bindRadioItem(rbData, rbArray, setting, editor) {
   if (rbData.Value) { // Value is 
      rbData.RadioButton.SubscribeChange(function (current) {
         // Only do this for the thing that is checked, not the one unchecked.
         if (current.IsChecked) {
            setting.SetValue(rbData.Value);
            if (editor) {
               editor.IsDisabled = true;
               if (setting.Value > 0) {
                  editor.Text = setting.ValueStr;
               }
            }
         }
      });
   } else {
      rbData.RadioButton.SubscribeChange(function (current) {
         // Only do this for the thing that is checked, not the one unchecked.
         if (current.IsChecked) {
            if ((rbData.Editor) && (rbData.Default) && (!rbData.Editor.Text)) {
               rbData.Editor.Text = rbData.Default;
            }
            if (editor) {
               editor.IsDisabled = false;
               setting.SetValueFromString(editor.Text);
            }
         }
      });
   }
}