# DOM Inspection Guide - Finding Real Selectors

## Purpose
This guide helps identify the actual CSS classes and HTML structure on sauce-demo.myshopify.com so we can replace placeholder selectors with real, working ones.

## Tools Needed
1. **Browser DevTools** (open with F12 in Chrome/Firefox)
2. **Element Inspector** (Ctrl+Shift+C or right-click → Inspect)
3. **Console** (F12 → Console tab)

## Elements to Inspect

### 1. Footer Section
**Current Issue**: Tests fail looking for `footer .footer-bottom` and `footer .footer-bar`

**Inspection Steps**:
1. Open sauce-demo.myshopify.com
2. Scroll to bottom of page
3. Right-click on footer → "Inspect"
4. Document the following:
   - Footer container selector: `<footer class="?" id="?">` or what tag/class wraps footer
   - Bottom bar classes: What CSS classes exist? `.footer-bottom`? `.footer-bar`?
   - Footer section structure: Are there `<section>` tags? What classes?
   - Copyright text location: Where is the copyright paragraph?

**Example Output to Look For**:
```html
<footer class="site-footer" id="footer-main">
  <div class="footer-top">
    <!-- Navigation sections here -->
  </div>
  <div class="footer-bottom">  <!-- THIS IS THE CLASS WE NEED -->
    <p>&copy; 2024 Retailer. All rights reserved.</p>
  </div>
</footer>
```

**Action if Not Found as Expected**:
- Document actual HTML structure
- Note CSS classes that DO exist
- Update selectors in FooterPage.java with real classes

---

### 2. Header Tagline Section 
**Current Issue**: Tests fail looking for `.tagline`, `.site-tagline`, `h1 + p`

**Inspection Steps**:
1. Scroll to top of page
2. Right-click on the tagline text (usually near/below the logo)
3. Inspect the element
4. Document:
   - Is there a `<span class="tagline">` or `<p class="tagline">`?
   - What's the actual CSS class? (screenshot helps)
   - What's the HTML tag (`<p>`, `<span>`, `<div>`)?
   - Can you select it with `.site-tagline`?

**Example Output to Look For**:
```html
<header>
  <div class="header-top">
    <img src="logo.png" alt="Retailer" />
    <p class="tagline">Welcome to our store</p>  <!-- THIS -->
  </div>
</header>
```

**Action if Not Found**:
- Document actual structure
- If no tagline exists, update test to skip this step
- If it exists with different class, update selector

---

### 3. Side Navigation Links
**Current Issue**: Tests can't find links like "Home", "About Us", "Blog", etc.

**Inspection Steps**:
1. Look for left sidebar or side navigation menu
2. Right-click on "Home" link → Inspect
3. Document:
   - What's the parent container selector? `.sidebar`? `.side-nav`? 
   - Where are the links located in DOM hierarchy?
   - Do they exist in a `<nav>` tag? `<aside>`? Plain `<div>`?
   - What classes are on the `<a>` tags?

**Example Output to Look For**:
```html
<aside class="sidebar">
  <nav class="side-menu">
    <a href="/home" class="nav-link">Home</a>
    <a href="/catalog" class="nav-link">Catalog</a>
    <a href="/blog" class="nav-link">Blog</a>
    <!-- more links -->
  </nav>
</aside>
```

**Action if Not Found**:
- Check if navigation is in main menu instead
- Check if navigation appears on different pages only
- Document actual selector that works

---

### 4. Social Media Icons
**Current Issue**: Tests fail for Facebook, Twitter, Instagram, Pinterest icons

**Inspection Steps**:
1. Look for social icons (usually header or footer)
2. Right-click on Facebook icon → Inspect
3. For each icon, document:
   - Is it an `<a>` tag? `<button>`? Icon inside `<a>`?
   - What's the href? Does it contain "facebook.com"?
   - Are there FontAwesome classes? `fa-facebook`, `fab`, etc.?
   - Are there data attributes? `data-social="facebook"`?

**Example Output to Look For**:
```html
<div class="social-icons">
  <a href="https://facebook.com/retailer" class="social-link facebook-link">
    <i class="fab fa-facebook"></i>
  </a>
  <a href="https://twitter.com/retailer" class="social-link twitter-link">
    <i class="fab fa-twitter"></i>
  </a>
  <!-- more icons -->
</div>
```

**Action if Not Found**:
- Check multiple locations (header, footer, sidebar)
- Check if icons are images instead of `<a>` tags
- Document what you actually find

---

### 5. Footer Section Headings
**Current Issue**: Tests look for h3 with exact text "About Us"

**Inspection Steps**:
1. Scroll to footer sections (usually 3-4 columns)
2. Right-click on "About Us" heading → Inspect
3. Document:
   - Is it `<h3>`? `<h2>`? `<h4>`?
   - What's the exact text? "About Us" or something else?
   - Any CSS classes on the heading?
   - Parent structure around heading?

**Example Output to Look For**:
```html
<footer>
  <div class="footer-column">
    <h3 class="footer-heading">About Us</h3>
    <ul>
      <li><a href="#">Our Story</a></li>
      <!-- links -->
    </ul>
  </div>
</footer>
```

**Action if Not Found**:
- Search for "About Us" text anywhere on page
- Check if it's spelled differently: "About US", "ABOUT US", etc.
- If not found anywhere, note this in test expectations

---

## Console Commands to Run

Open browser console (F12) and run these commands:

### Find footer element
```javascript
document.querySelector('footer')  // Returns footer element or null
document.querySelectorAll('footer .footer-bottom')  // Returns matching elements
document.querySelectorAll('[class*="footer-bottom"]')  // Find anything with "footer-bottom" in class
```

### Find tagline
```javascript
document.querySelector('.tagline')  // Check if this class exists
document.querySelector('header p')  // Find paragraphs in header
document.querySelector('header span')  // Find spans in header
// Look at the results to find tagline
```

### Find side nav
```javascript
document.querySelector('.sidebar')  // Check sidebar
document.querySelector('aside nav')  // Check aside nav
document.querySelectorAll('a')  // Get all links, check if Home, Catalog, etc. are there
```

### Find social icons
```javascript
document.querySelectorAll('[href*="facebook"]')  // Find links to facebook
document.querySelectorAll('[href*="twitter"]')  // Find links to twitter
document.querySelectorAll('.fa-facebook')  // Find FontAwesome facebook icon
document.querySelectorAll('[class*="social"]')  // Find anything with "social" in class
```

## Documentation Template

For each element found, fill this out:

```
ELEMENT: [Element Name]
────────────────────────────────────────────

CSS Selector that works:
  - Primary: .foo, header .bar
  - Fallback 1: div[role='contentinfo']
  - Fallback 2: [class*='footer']

XPath that works:
  - Primary: //footer//h3[text()='About Us']
  - Fallback 1: //h3[contains(text(), 'About')]

Element location: [top/bottom/left-sidebar/right-sidebar/other]
HTML structure: [h3 inside div.footer-section inside footer]
Text content: "About Us"
Current test selector: footer .footer-bottom
Status: ✓ FOUND / ✗ NOT FOUND

Notes: [Any additional observations]
```

## Quick Fixes to Try

If elements don't match expected selectors:

1. **Footer Bottom Bar** - Try these in console:
   ```javascript
   document.querySelector('footer')  // Try just footer
   document.querySelector('[role="contentinfo"]')  // Try ARIA role
   document.querySelector('div.site-footer')  // Try common footer class
   ```

2. **Tagline** - Try these:
   ```javascript
   document.querySelector('header p')  // Find any header paragraph
   document.querySelector('.site-tagline')  // Try site-tagline class
   document.querySelector('h1 + p')  // Try h1 + p selector
   ```

3. **Side Nav** - Try these:
   ```javascript
   document.querySelector('nav a')  // Find nav link
   document.querySelector('aside a')  // Find aside link
   document.querySelector('[href="/"]')  // Find home link by href
   ```

## Manual Testing Process

1. **Before Updating Code**:
   - Visit website
   - Open DevTools console
   - Test selectors mentioned above
   - **Document which ones return elements**

2. **Update Selectors**:
   - Replace placeholder selectors with working ones
   - Use primary selector that definitely works
   - Keep fallbacks for robustness

3. **Re-test**:
   - Run tests again
   - Elements should now be found
   - Remove try-catch error handling (make tests fail if elements missing)

## Expected Outcomes

After inspection, you should have:

- ✅ Real CSS selectors that work on sauce-demo.myshopify.com
- ✅ Knowledge of HTML structure for each element
- ✅ XPath alternatives if CSS fails
- ✅ List of elements that don't exist (to skip in tests)
- ✅ Document mapping placeholder → real selectors

## Next Phase

Once you have this information:

1. Update `HeaderPage.java` with real selectors
2. Update `FooterPage.java` with real selectors
3. Update `HeaderNavigationSteps.java` with real selectors
4. Remove `try-catch` blocks (tests should pass cleanly)
5. Run tests again - they should pass ✅

---

**Note**: The current error-handling implementation allows tests to proceed even with wrong selectors. This gives you time to inspect the real website and find correct selectors before fixing them permanently.
