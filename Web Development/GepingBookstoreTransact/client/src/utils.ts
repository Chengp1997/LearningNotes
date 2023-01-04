// Correct number formatting for currency

// From https://flaviocopes.com/how-to-format-number-as-currency-javascript/
const PriceFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  minimumFractionDigits: 2,
});

export function asDollarsAndCents(cents: number): string {
  if (!cents) return "?";
  return PriceFormatter.format(cents / 100.0);
}

// From https://github.com/validatorjs
const US_MOBILE_PHONE_PATTERN =
  /^((\+1|1)?( |-)?)?(\([2-9][0-9]{2}\)|[2-9][0-9]{2})( |-)?([2-9][0-9]{2}( |-)?[0-9]{4})$/;
export function isMobilePhone(input: string): boolean {
  return US_MOBILE_PHONE_PATTERN.test(input);
}

// From https://github.com/validatorjs
/* eslint-disable max-len */
const creditCard =
  /^(?:4[0-9]{12}(?:[0-9]{3,6})?|5[1-5][0-9]{14}|(222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}|6(?:011|5[0-9][0-9])[0-9]{12,15}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11}|6[27][0-9]{14}|^(81[0-9]{14,17}))$/;
/* eslint-enable max-len */

export function isCreditCard(str: string) {
  const sanitized = str.replace(/[- ]+/g, "");

  if (!creditCard.test(sanitized)) {
    return false;
  }

  let sum = 0;
  let digit;
  let tmpNum;
  let shouldDouble;

  for (let i = sanitized.length - 1; i >= 0; i--) {
    digit = sanitized.substring(i, i + 1);
    tmpNum = parseInt(digit, 10);

    if (shouldDouble) {
      tmpNum *= 2;

      if (tmpNum >= 10) {
        sum += (tmpNum % 10) + 1;
      } else {
        sum += tmpNum;
      }
    } else {
      sum += tmpNum;
    }

    shouldDouble = !shouldDouble;
  }

  return !!(sum % 10 === 0 ? sanitized : false);
}
