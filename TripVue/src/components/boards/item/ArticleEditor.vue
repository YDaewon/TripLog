<template>
	<div>
		<div class="main-container">
			<div class="editor-container editor-container_classic-editor editor-container_include-block-toolbar" ref="editorContainerElement">
				<div class="editor-container__editor">
					<div ref="editorElement">
						<ckeditor v-if="isLayoutReady" v-model="content" :editor="editor" :config="config" />
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import {
	ClassicEditor,
	AccessibilityHelp,
	Autoformat,
	Autosave,
	BlockQuote,
	BlockToolbar,
	Bold,
	Essentials,
	FullPage,
	GeneralHtmlSupport,
	Heading,
	HtmlComment,
	HtmlEmbed,
	Indent,
	IndentBlock,
	Italic,
	Link,
	Paragraph,
	SelectAll,
	ShowBlocks,
	SourceEditing,
	Table,
	TableCaption,
	TableCellProperties,
	TableColumnResize,
	TableProperties,
	TableToolbar,
	TextTransformation,
	Underline,
	Undo
} from 'ckeditor5';

import 'ckeditor5/ckeditor5.css';

import { ref, onMounted } from "vue";

const isLayoutReady = ref(false);
const config = ref(null);

onMounted(() => {
  config.value = {
    toolbar: {
      items: [
        'undo',
        'redo',
        '|',
        'sourceEditing',
        'showBlocks',
        '|',
        'heading',
        '|',
        'bold',
        'italic',
        'underline',
        '|',
        'link',
        'insertTable',
        'blockQuote',
        'htmlEmbed',
        '|',
        'outdent',
        'indent'
      ],
      shouldNotGroupWhenFull: false
    },
    plugins: [
      AccessibilityHelp,
      Autoformat,
      Autosave,
      BlockQuote,
      BlockToolbar,
      Bold,
      Essentials,
      FullPage,
      GeneralHtmlSupport,
      Heading,
      HtmlComment,
      HtmlEmbed,
      Indent,
      IndentBlock,
      Italic,
      Link,
      Paragraph,
      SelectAll,
      ShowBlocks,
      SourceEditing,
      Table,
      TableCaption,
      TableCellProperties,
      TableColumnResize,
      TableProperties,
      TableToolbar,
      TextTransformation,
      Underline,
      Undo
    ],
    blockToolbar: ['bold', 'italic', '|', 'link', 'insertTable', '|', 'outdent', 'indent'],
    heading: {
      options: [
        {
          model: 'paragraph',
          title: 'Paragraph',
          class: 'ck-heading_paragraph'
        },
        {
          model: 'heading1',
          view: 'h1',
          title: 'Heading 1',
          class: 'ck-heading_heading1'
        },
        {
          model: 'heading2',
          view: 'h2',
          title: 'Heading 2',
          class: 'ck-heading_heading2'
        },
        {
          model: 'heading3',
          view: 'h3',
          title: 'Heading 3',
          class: 'ck-heading_heading3'
        },
        {
          model: 'heading4',
          view: 'h4',
          title: 'Heading 4',
          class: 'ck-heading_heading4'
        },
        {
          model: 'heading5',
          view: 'h5',
          title: 'Heading 5',
          class: 'ck-heading_heading5'
        },
        {
          model: 'heading6',
          view: 'h6',
          title: 'Heading 6',
          class: 'ck-heading_heading6'
        }
      ]
    },
    htmlSupport: {
      allow: [
        {
          name: /^.*$/,
          styles: true,
          attributes: true,
          classes: true
        }
      ]
    },
    initialData:
      '<h2>Congratulations on setting up CKEditor 5! 🎉</h2>\n<p>\n    You\'ve successfully created a CKEditor 5 project...</p>',
    link: {
      addTargetToExternalLinks: true,
      defaultProtocol: 'https://',
      decorators: {
        toggleDownloadable: {
          mode: 'manual',
          label: 'Downloadable',
          attributes: {
            download: 'file'
          }
        }
      }
    },
    placeholder: 'Type or paste your content here!',
    table: {
      contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties']
    }
  };

  isLayoutReady.value = true;
});

const props = defineProps({
  article:  Object,
  userInfo: Object,
});

const content = props.article.value.content;



</script>
